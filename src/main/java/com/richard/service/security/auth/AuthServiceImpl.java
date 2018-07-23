package com.richard.service.security.auth;

import com.mysql.jdbc.StringUtils;
import com.richard.service.constant.ErrorCode;
import com.richard.service.constant.ProjectConfig;
import com.richard.service.domain.user.RepositoryUser;
import com.richard.service.domain.common.RestResult;
import com.richard.service.domain.user.User;
import com.richard.service.security.demain.bean.JwtUser;
import com.richard.service.security.demain.bean.UserRole;
import com.richard.service.security.demain.RoleRepository;
import com.richard.service.service.common.UploadService;
import com.richard.service.utils.RestGenerator;
import com.richard.service.utils.TokenUtil;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AuthServiceImpl implements AuthService {

    private AuthenticationManager authenticationManager;
    private UserDetailsService userDetailsService;
    private TokenUtil jwtTokenUtil;
    private RepositoryUser repositoryUser;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Autowired
    private UploadService uploadService;
    @Autowired
    RoleRepository roleRepository;

    @Autowired
    public AuthServiceImpl(

            UserDetailsService userDetailsService,
            TokenUtil jwtTokenUtil,
            RepositoryUser repositoryUser,
            AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.repositoryUser = repositoryUser;
    }

    @Override
    public RestResult register(User userToAdd) {
        /**
         {
         "name":"richae",
         "qqOpenId" or "wxUnionId"
         "header":"http://q.qlogo.cn/qqapp/1106309587/431B68A936EE7BDBCB3D18728B8D5E4A/100"
         }
         */
        final String qqOpenId = userToAdd.getQqOpenId();
        final String wxUnionId = userToAdd.getWxUnionId();
        final String header = userToAdd.getHeader();
        String name = userToAdd.getName();

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        User resultUser;
        String tempUniqueName;
        if(!StringUtils.isNullOrEmpty(qqOpenId)){
            resultUser = repositoryUser.findByQqOpenId(qqOpenId);
            tempUniqueName = qqOpenId;
        }else if(!StringUtils.isNullOrEmpty(wxUnionId)){
            resultUser = repositoryUser.findByWxUnionId(wxUnionId);
            tempUniqueName = wxUnionId;
        }else{
            return RestGenerator.genErrorResult("请用qq或wx登录");
        }
        if(resultUser == null) {
            //上传头像
            List<String> urls = new ArrayList<>();
            urls.add(header);
            List<String> resultHeaders = uploadService.uploadUrlFile(urls);
            userToAdd.setHeader(resultHeaders.get(0));

            //qqOpenId or wxUnionId 作为唯一值，并且加密作为密码，
            userToAdd.setUniqueName(tempUniqueName);
            userToAdd.setPassword(encoder.encode(tempUniqueName));
            userToAdd.setLastPasswordResetDate(new Date());
            List<UserRole> listUserRoles = new ArrayList<>();
            listUserRoles.add(roleRepository.findByName(ProjectConfig.USER_RULE_GENERAL));
            userToAdd.setUserRoles(listUserRoles);

            name = name + Float.toHexString(System.currentTimeMillis());
            userToAdd.setName(name);
            try {
                repositoryUser.save(userToAdd);
            }catch (ConstraintViolationException e){
                return RestGenerator.genErrorResult("注册失败，请联系管理员");
            }
        }

        resultUser = repositoryUser.findByUniqueName(tempUniqueName);
        String token = login(tempUniqueName, tempUniqueName);
        resultUser.setToken(token);
        repositoryUser.save(resultUser);

        Map<String, Object> map = new HashMap<>();
        map.put("user", resultUser);
        map.put("token", token);
        return RestGenerator.genResult(ErrorCode.OK, map, "欢迎归来");
    }

    @Override
    public String login(String username, String password) {
        UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(username, password);
        // Perform the security
        final Authentication authentication = authenticationManager.authenticate(upToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // Reload password post-security so we can generate token
        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        final String token = jwtTokenUtil.generateToken(username,userDetails);
        return token;
    }

    @Override
    public String refresh(String oldToken) {
        final String token = oldToken.substring(tokenHead.length());
        String username = jwtTokenUtil.getUsernameFromToken(token);
        JwtUser user = (JwtUser) userDetailsService.loadUserByUsername(username);
        if (jwtTokenUtil.canTokenBeRefreshed(token, user.getLastPasswordResetDate())){
            return jwtTokenUtil.refreshToken(token);
        }
        return null;
    }
}

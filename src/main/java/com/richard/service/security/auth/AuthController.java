package com.richard.service.security.auth;

import com.richard.service.constant.ErrorCode;
import com.richard.service.domain.common.RestResult;
import com.richard.service.domain.user.User;
import com.richard.service.service.common.UploadService;
import com.richard.service.utils.RestGenerator;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/auth")
public class AuthController {
    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    private AuthService authService;

    @RequestMapping(value = "/auth", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(
            @RequestBody User authenticationRequest) throws AuthenticationException {
        final String token = authService.login(authenticationRequest.getName(), authenticationRequest.getPassword());
        // Return the token
        return ResponseEntity.ok(token);
    }

    @RequestMapping(value = "/refresh", method = RequestMethod.GET)
    public ResponseEntity<?> refreshAndGetAuthenticationToken(
            HttpServletRequest request) throws AuthenticationException {
        String token = request.getHeader(tokenHeader);
        String refreshedToken = authService.refresh(token);
        if(refreshedToken == null) {
            return ResponseEntity.badRequest().body(null);
        } else {
            return ResponseEntity.ok(refreshedToken);
        }
    }

    @ApiOperation(value = "登录注册统一接口")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public RestResult register(@RequestBody Map<String, String> params) throws AuthenticationException {
        String name = params.get("name");
        String qqOpenId = params.get("qqOpenId");
        String wxUnionId = params.get("wxUnionId");
        String header = params.get("header");
        int sex = Integer.parseInt(params.get("sex"));

        User userToAdd = new User(name,qqOpenId,wxUnionId,header, sex);
        return authService.register(userToAdd);
    }
}

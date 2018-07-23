package com.richard.service.interceptor;

import com.richard.service.domain.user.RepositoryUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * by Richard on 2017/8/31
 * desc:
 */
public class TokenInterceptor implements HandlerInterceptor {
    private Logger logger =  LoggerFactory.getLogger(this.getClass());
    @Autowired
    RepositoryUser repositoryUser;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        logger.debug(">>>TokenInterceptor>>>>>>>在请求之前");

//        String token = httpServletRequest.getHeader("token");
//        if(!StringUtils.isEmpty(token)){
//            User user = userRepository.findByToken(token);
//            if(user != null){
//                return true;// 只有返回true才会继续向下执行，返回false取消当前请求
//            }
//        }
//        throw new CommonException(ErrorCode.ERROR_TOKEN);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        logger.debug(">>>TokenInterceptor>>>>>>>在请求进行中");
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        logger.debug(">>>TokenInterceptor>>>>>>>在请求结束");
    }
}

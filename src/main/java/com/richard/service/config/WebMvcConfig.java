package com.richard.service.config;

import com.richard.service.interceptor.TokenInterceptor;
import com.richard.service.json.JsonReturnHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

/**
 * by Richard on 2017/8/27
 * desc:
 */
//@Configuration
class WebMvcConfig extends WebMvcConfigurerAdapter {
    @Bean
    public TokenInterceptor tokenInterceptor() {
        return new TokenInterceptor();//自己创建一下这个Spring Bean，这样就能在Spring映射这个拦截器前，把拦截器中的依赖注入给完成了
    }

    @Bean
    public JsonReturnHandler jsonReturnHandler(){
        return new JsonReturnHandler();//初始化json过滤器
    }
    @Override
    public void addReturnValueHandlers(//Json处理和过滤
            List<HandlerMethodReturnValueHandler> returnValueHandlers) {
        returnValueHandlers.add(jsonReturnHandler());
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
        super.addResourceHandlers(registry);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(userInterceptor()).addPathPatterns("/**").excludePathPatterns("/user/**");
        registry.addInterceptor(tokenInterceptor()).addPathPatterns("/pic/**").addPathPatterns("/auth/**");
        super.addInterceptors(registry);
    }
}

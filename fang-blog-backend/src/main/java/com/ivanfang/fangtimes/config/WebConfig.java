package com.ivanfang.fangtimes.config;

import com.ivanfang.fangtimes.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // execute login authorization except logging or registering
        registry.addInterceptor(loginInterceptor)
                .excludePathPatterns("/user/login", "/user/register");
    }

}

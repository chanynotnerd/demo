package com.ssamz.demo.config;

import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class JBlogWebMvcConfiguration implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry)
    {
        registry.addInterceptor(
                new AuthenticateInterceptor()).addPathPatterns("/");
    }
}

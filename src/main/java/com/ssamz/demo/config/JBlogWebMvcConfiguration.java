package com.ssamz.demo.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@Configuration
public class JBlogWebMvcConfiguration implements WebMvcConfigurer {
    @Bean("messageSource")
    public MessageSource messageSource()
    {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("message/messageSource");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    @Bean
    public LocaleResolver localeResolver()
    {
        return new SessionLocaleResolver();
    }

    @Bean
    public ModelMapper modelMapper()
    {
        return new ModelMapper();
        // 블로그 시스템은 엔티티 클래스를 이용하여 사용자가 입력한 값을 전달받지만
        // 사용자 입력값을 받을 땐 DTO 클래스를 이용해야 한다.
        // 이런 식으로 사용하다 보면 누락될 수 있으므로
        // 자바 객체가 가진 멤버변수 값들을 원하는 객체의 멤버변수에 자동으로 매핑시켜주는
        // ModelMapper 라이브러리를 사용하는 이유이다.
    }
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor()
    {
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("lang");
        return localeChangeInterceptor;
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry)
    {
        registry.addInterceptor(
                new AuthenticateInterceptor()).addPathPatterns("/", "/post/**");
        registry.addInterceptor(localeChangeInterceptor());
    }
}

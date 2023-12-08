package com.ssamz.demo.config;

import com.ssamz.demo.security.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class JBloWebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception
    {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }
    protected void configure(AuthenticationManagerBuilder auth) throws Exception
    {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        // 인증 없이 접근을 허용하는 경로
        http.authorizeRequests().antMatchers("/webjars/**", "/js/**", "/image/**",
                "/", "/auth/**", "/oauth/**").permitAll();

        // 이외의 경로는 인증 필요
        http.authorizeRequests().anyRequest().authenticated();

        // CSRF 토큰을 받지 않음
        http.csrf().disable();

        // 사용자 정의 로그인 화면 제공
        http.formLogin().loginPage("/auth/login");

        // 로그인 요청 URI를 변경한다.
        http.formLogin().loginProcessingUrl("/auth/securitylogin");

        // 로그아웃 설정
        http.logout().logoutUrl("/auth/logout").logoutSuccessUrl("/");
    }
}

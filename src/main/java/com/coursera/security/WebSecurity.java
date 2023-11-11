package com.coursera.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurity {


    private final CustomUserDetailsService userDetailService;

    public WebSecurity(CustomUserDetailsService userDetailService) {
        this.userDetailService = userDetailService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // telling security to don't check the below urls
        http.authorizeRequests().mvcMatchers("/login","/authenticate").permitAll();

        // telling to check all urls
        http.authorizeRequests().anyRequest().authenticated();

        // setting login  page and authentication
        http.userDetailsService(userDetailService).formLogin().loginPage("/login").loginProcessingUrl("/authenticate")
                .failureForwardUrl("/login?error")
                .defaultSuccessUrl("/home",true);

        return http.build();

    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }



}

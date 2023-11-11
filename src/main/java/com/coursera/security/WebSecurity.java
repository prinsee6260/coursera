package com.coursera.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
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
    protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // telling security to don't check the below urls
        return http.authorizeRequests().mvcMatchers("/login","/authenticate").permitAll().
                // telling to check all urls
                and().authorizeRequests().anyRequest().authenticated().
                // setting login  page and authentication
                and().userDetailsService(userDetailService).formLogin().loginPage("/login").failureForwardUrl("/login?error")
                .defaultSuccessUrl("/home",true).loginProcessingUrl("/authenticate").and().build();


    }
}

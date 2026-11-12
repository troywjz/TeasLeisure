package com.zbkj.front.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Front public API filter chain. Endpoint-specific token validation remains in FrontTokenInterceptor.
 */
@Configuration
@EnableWebSecurity
public class CloseSecurityConfig {

    @Bean
    public SecurityFilterChain frontSecurityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.authorizeHttpRequests(authorize -> authorize.anyRequest().permitAll());
        http.logout(logout -> logout.permitAll());
        return http.build();
    }
}

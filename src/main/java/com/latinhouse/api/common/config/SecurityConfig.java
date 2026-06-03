package com.latinhouse.api.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, "/api/profile").permitAll()
                        .requestMatchers(HttpMethod.PATCH, "/api/profile/*/instructor").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/profiles").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/lesson").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/lesson/random").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/lessons").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/lessons/*").permitAll()
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html").permitAll()
                        .anyRequest().authenticated()
                )
                .build();
    }
}

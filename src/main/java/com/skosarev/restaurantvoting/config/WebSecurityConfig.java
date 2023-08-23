package com.skosarev.restaurantvoting.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    // Configuring HttpSecurity
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/api/auth/signup").permitAll()
                        .requestMatchers("/error").permitAll()
                        .requestMatchers("/api/profile").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/restaurants").authenticated()
                        .requestMatchers("/api/restaurants/**").hasRole("ADMIN")
                        .requestMatchers("/api/people/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/dishes").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/votes").hasRole("ADMIN")
                        .anyRequest().authenticated()
                ).csrf(AbstractHttpConfigurer::disable)
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

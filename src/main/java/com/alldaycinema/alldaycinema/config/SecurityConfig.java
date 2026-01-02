package com.alldaycinema.alldaycinema.config;

import com.alldaycinema.alldaycinema.security.AuthEntryPointJwt;
import com.alldaycinema.alldaycinema.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private AuthEntryPointJwt authEntryPointJwt;

    private static final String[] PUBLIC_ENDPOINTS = {
            "/api/auth/login",
            "/api/auth/signup",
            "/api/auth/validate-email",
            "/api/auth/verify-email",
            "/api/auth/resend-verification",
            "/api/auth/forgot-password",
            "/api/auth/reset-password"
    };

    @Bean
    public PasswordEncoder passwordEncoder(){
//        return org.springframework.security.crypto.password.NoOpPasswordEncoder.getInstance();  //demo
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.csrf(csrf->csrf.disable())
                .cors(cors->{})
                .exceptionHandling(exception->exception
                        .authenticationEntryPoint(authEntryPointJwt))
                .authorizeHttpRequests(
                        auth->auth.requestMatchers(PUBLIC_ENDPOINTS)
                                .permitAll().anyRequest().authenticated())
                .sessionManagement(session->session.sessionCreationPolicy(
                        SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}

package com.moa.global.security.configuration;

import com.moa.global.security.filter.JwtAuthenticationFilter;
import com.moa.global.security.handler.UserAuthenticationSuccessHandler;
import com.moa.global.security.jwt.JwtProvider;
import com.moa.global.security.utils.UserDataResponder;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Configuration
public class SecurityConfiguration {

    private final JwtProvider jwtProvider;
    private final UserDataResponder userDataResponder;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        AuthenticationManager authenticationManager = authenticationManager(httpSecurity.getSharedObject(AuthenticationConfiguration.class));

        httpSecurity
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .authenticationManager(authenticationManager)
                .authorizeHttpRequests(authorize -> authorize
                        .anyRequest().permitAll()
                );

        addCustomFilters(httpSecurity, authenticationManager);

        return httpSecurity.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOriginPattern("http://localhost:3000");

        configuration.setAllowedHeaders(List.of("Content-Type", "Authorization"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PATCH", "DELETE", "OPTIONS"));
        configuration.addExposedHeader("Authorization");
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return request -> configuration;
    }

    public void addCustomFilters(HttpSecurity httpSecurity, AuthenticationManager authenticationManager) {
        JwtAuthenticationFilter jwtAuthenticationFilter = getJwtAuthenticationFilter(authenticationManager);

        httpSecurity
                .addFilter(jwtAuthenticationFilter);
    }

    private JwtAuthenticationFilter getJwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager, jwtProvider, userDataResponder);
        jwtAuthenticationFilter.setFilterProcessesUrl("/api/v1/auth/login");
        jwtAuthenticationFilter.setAuthenticationSuccessHandler(new UserAuthenticationSuccessHandler());

        return jwtAuthenticationFilter;
    }

}

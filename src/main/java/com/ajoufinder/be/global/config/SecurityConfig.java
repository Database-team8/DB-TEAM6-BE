package com.ajoufinder.be.global.config;

import com.ajoufinder.be.global.service.CustomUserDetailsService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final CustomUserDetailsService customUserDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Swagger로 테스트하려면 disable 필요
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/signup", "auth/login", "/v3/api-docs/**", "/swagger-ui/**").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .sessionManagement(sess -> sess
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) // 세션 기반 인증
                )
                .formLogin(AbstractHttpConfigurer::disable) // 👉 기본 로그인 폼 사용 안 함
                .logout(logout -> logout
                        .logoutUrl("auth/logout")
                        .logoutSuccessHandler((req, res, auth) -> res.setStatus(HttpServletResponse.SC_OK))
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                )
                .userDetailsService(customUserDetailsService)
                .authenticationProvider(daoAuthenticationProvider());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(customUserDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authBuilder
                .userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder());

        return authBuilder.build();
    }

}

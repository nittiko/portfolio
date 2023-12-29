package com.sample.FreeCommunity.config;

import com.sample.FreeCommunity.user.constant.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public Argon2PasswordEncoder passwordEncoder() {
        return Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http

                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeRequests((authorizeRequests) ->
                        authorizeRequests
                                .requestMatchers("/admin/**").hasRole(String.valueOf(Role.ADMIN))
                                .requestMatchers("/logOut", "/user/**").authenticated()
                                .requestMatchers("/**").permitAll())
                .logout((logoutConfig) ->
                        logoutConfig.logoutSuccessUrl("/"))
                .formLogin(configurer -> {
                    configurer.loginPage("/login"); // 권한 접근 실패 시 로그인 페이지로 이동
                    configurer.defaultSuccessUrl("/"); // 로그인 성공 시 이동할 페이지
                    configurer.failureUrl("/logIn");
                }
                )
//                .csrf().ignoringRequestMatchers("/mail/**")
                .csrf().disable()
                .headers()
                .frameOptions().sameOrigin();


        return http.build();
    }

    public void configure(WebSecurity web)throws Exception{
        web.ignoring().requestMatchers("/h2-console/**");
    }

    @Bean
    public AuthenticationManager authenticationManager (
            AuthenticationConfiguration authenticationConfiguration
    ) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }
}
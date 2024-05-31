package com.maximum.koreaArtSchool.config;

import com.maximum.koreaArtSchool.filter.AdminAccessFilter;
import com.maximum.koreaArtSchool.service.AdminDetailsService;
import com.maximum.koreaArtSchool.service.EvaluatorDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final AdminDetailsService adminDetailsService;
    private final EvaluatorDetailsService evaluatorDetailsService;
    private final AdminAccessFilter adminAccessFilter;

    @Bean
    public WebSecurityCustomizer configure() {
        return (web -> web.ignoring()
                .requestMatchers(
                        new AntPathRequestMatcher("/assets/**"),
                        new AntPathRequestMatcher("/static/**")));
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeRequests(auth -> auth
                        .requestMatchers(
                                new AntPathRequestMatcher("/login"),
                                new AntPathRequestMatcher("/signup"),
                                new AntPathRequestMatcher("/find/**"),
                                new AntPathRequestMatcher("/user/**")
                        ).permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/evaluator/**").hasRole("EVALUATOR")
                        .anyRequest().authenticated())
                .formLogin(formLogin -> formLogin
                        .loginPage("/login")
                        .usernameParameter("email").passwordParameter("pswd")
                        .defaultSuccessUrl("/", true))
                .logout(logout -> logout
                        .logoutSuccessUrl("/login")
                        .invalidateHttpSession(true))
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(adminAccessFilter, UsernamePasswordAuthenticationFilter.class).build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder) throws Exception {
        DaoAuthenticationProvider adminAuthProvider = new DaoAuthenticationProvider();
        adminAuthProvider.setUserDetailsService(adminDetailsService);
        adminAuthProvider.setPasswordEncoder(bCryptPasswordEncoder);

        DaoAuthenticationProvider evaluatorAuthProvider = new DaoAuthenticationProvider();
        evaluatorAuthProvider.setUserDetailsService(evaluatorDetailsService);
        evaluatorAuthProvider.setPasswordEncoder(bCryptPasswordEncoder);

        return new ProviderManager(Arrays.asList(adminAuthProvider, evaluatorAuthProvider));
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
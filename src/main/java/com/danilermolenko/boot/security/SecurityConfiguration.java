package com.danilermolenko.boot.security;

import com.danilermolenko.boot.auth.UserApplicationService;
import com.danilermolenko.boot.jwt.JwtConfig;
import com.danilermolenko.boot.jwt.JwtTokenVerifier;
import com.danilermolenko.boot.jwt.JwtUsernameAndPasswordAuthenticationFilter;
import com.danilermolenko.boot.models.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.crypto.SecretKey;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {
    private final PasswordEncoder passwordEncoder;
    private final UserApplicationService userApplicationService;
    private final SecretKey secretKey;
    private final JwtConfig jwtConfig;
    @Autowired
    public SecurityConfiguration(PasswordEncoder passwordEncoder,
                                 UserApplicationService userApplicationService,
                                 SecretKey secretKey,
                                 JwtConfig jwtConfig) {
        this.passwordEncoder = passwordEncoder;
        this.userApplicationService = userApplicationService;
        this.secretKey = secretKey;
        this.jwtConfig = jwtConfig;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(csrf -> csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()));
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http
                .addFilter(new JwtUsernameAndPasswordAuthenticationFilter(authenticationManager(), jwtConfig, secretKey))
                .addFilterAfter(new JwtTokenVerifier(secretKey, jwtConfig), JwtUsernameAndPasswordAuthenticationFilter.class)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(new AntPathRequestMatcher("/index.html")).permitAll()
                        .requestMatchers("/api/**").hasRole(Roles.ADMIN.name())
                        .anyRequest().authenticated());


        return http.build();
    }
    @Bean
    public AuthenticationManager authenticationManager(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(userApplicationService);
        return new ProviderManager(provider);
    }

}

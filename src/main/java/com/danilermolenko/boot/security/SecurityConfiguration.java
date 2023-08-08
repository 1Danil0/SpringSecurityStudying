package com.danilermolenko.boot.security;

import com.danilermolenko.boot.models.Permissions;
import com.danilermolenko.boot.models.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public SecurityConfiguration(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(csrf -> csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()));
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(new AntPathRequestMatcher("/index.html")).permitAll()

                        .requestMatchers("/api/**").hasRole(Roles.ADMIN.name())
//                        .requestMatchers(HttpMethod.GET, "/management/api/**").hasAnyRole(Roles.ADMIN.name(), Roles.ADMIN_TRAINEE.name())
//                        .requestMatchers(HttpMethod.POST, "/management/api/**").hasAuthority(Permissions.USER_WRITE.getPermission())
//                        .requestMatchers(HttpMethod.DELETE, "/management/api/**").hasAuthority(Permissions.USER_WRITE.getPermission())
//                        .requestMatchers(HttpMethod.PUT, "/management/api/**").hasAuthority(Permissions.USER_WRITE.getPermission())
                        .anyRequest().authenticated());
//                        .anyRequest().permitAll())
        http
//                .formLogin().permitAll();
                .formLogin(form -> form
                        .loginPage("/login")
                        .permitAll()
                        .defaultSuccessUrl("/successLogin", true)
                        .usernameParameter("username")
                        .passwordParameter("password"))
                .rememberMe(rem -> rem
                        .tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(21))
                        .key("secured")
                        .rememberMeParameter("remember-me"));
        http
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .clearAuthentication(true)
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID", "XSRF-TOKEN", "remember-me")
                        .logoutSuccessUrl("/login"));

//                .httpBasic(Customizer.withDefaults());
        return http.build();
    }
    @Bean
    public UserDetailsService userDetails(){
        UserDetails danil = User.builder()
                .username("danil")
                .password(passwordEncoder.encode("danil"))
//                .roles(Roles.USER.name())
                .authorities(Roles.USER.getGrantedAuthorities())
                .build();
        UserDetails yasya = User.builder()
                .username("yasya")
                .password(passwordEncoder.encode("yasya"))
//                .roles(Roles.ADMIN.name())
                .authorities(Roles.ADMIN.getGrantedAuthorities())
                .build();
        UserDetails tanya = User.builder()
                .username("tanya")
                .password(passwordEncoder.encode("tanya"))
//                .roles(Roles.ADMIN_TRAINEE.name())
                .authorities(Roles.ADMIN_TRAINEE.getGrantedAuthorities())
                .build();
        return new InMemoryUserDetailsManager(danil, yasya, tanya);
    }
}

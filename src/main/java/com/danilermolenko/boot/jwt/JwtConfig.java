package com.danilermolenko.boot.jwt;


import com.google.common.net.HttpHeaders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;
@Configuration
@EnableConfigurationProperties
public class JwtConfig {
    @Value("${secretKey}")
    private String secretKey;
    @Value("${tokenPrefix}")
    private String tokenPrefix;
    @Value("${tokenExpirationAfterDays}")
    private int tokenExpirationAfterDays;

    public JwtConfig() {
    }
    @Bean
    public SecretKey getSecretkey(){
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }
    @Bean
    public String getAuthorizationHeader(){
        return HttpHeaders.AUTHORIZATION;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getTokenPrefix() {
        return tokenPrefix;
    }

    public void setTokenPrefix(String tokenPrefix) {
        this.tokenPrefix = tokenPrefix;
    }

    public Integer getTokenExpirationAfterDays() {
        return tokenExpirationAfterDays;
    }

    public void setTokenExpirationAfterDays(Integer tokenExpirationAfterDays) {
        this.tokenExpirationAfterDays = tokenExpirationAfterDays;
    }
}

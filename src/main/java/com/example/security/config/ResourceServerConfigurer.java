package com.example.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;

@Configuration
@EnableResourceServer
public class ResourceServerConfigurer extends ResourceServerConfigurerAdapter {
    private final DefaultTokenServices jwtTokenServices;

    private final TokenStore tokenStore;

    @Value("${security.jwt.resource-ids}")
    private String resourceIds;

    @Autowired
    public ResourceServerConfigurer(DefaultTokenServices jwtTokenServices, TokenStore tokenStore) {
        this.jwtTokenServices = jwtTokenServices;
        this.tokenStore = tokenStore;
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources
                .resourceId(resourceIds)
                .tokenServices(jwtTokenServices)
                .tokenStore(tokenStore);
    }

    //da rivedere magari mettere eccezione per il percorso user
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .anyRequest().permitAll();
    }
}

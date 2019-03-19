package com.example.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;

@Configuration
public class AuthorizationServerConfigurer extends AuthorizationServerConfigurerAdapter {
    @Value("${security.jwt.client-id}")
    private String clientId;

    @Value("${security.jwt.client-secret}")
    private String clientSecret;

    @Value("${security.jwt.grant-type}")
    private String grantType;

    @Value("${security.jwt.scope-read}")
    private String scopeRead;

    @Value("${security.jwt.scope-write}")
    private String scopeWrite = "write";

    @Value("${security.jwt.resource-ids}")
    private String resourceIds;

    private final AuthenticationManager authenticationManager;

    private final UserDetailsService userDetailsService;

    private final DefaultTokenServices defaultTokenServices;

    private final AccessTokenConverter accessTokenConverter;

    @Autowired
    public AuthorizationServerConfigurer(AuthenticationManager authenticationManager, UserDetailsService userDetailsService, DefaultTokenServices defaultTokenServices, AccessTokenConverter accessTokenConverter) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.defaultTokenServices = defaultTokenServices;
        this.accessTokenConverter = accessTokenConverter;
    }

    /**
     * This method defines which clients applications are registered with the authentication service.
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients
                .inMemory()
                .withClient(clientId)
                .secret(clientSecret)
                .authorizedGrantTypes(grantType)
                .scopes(scopeRead, scopeWrite)
                .resourceIds(resourceIds);
    }

    /**
     * This method defines the different components used within the AuthenticationServerConfigurer.
     * This code is telling spring to use the default authentication manager and user details service
     * that comes up with spring.
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .tokenServices(defaultTokenServices)
                .authenticationManager(authenticationManager)
                .accessTokenConverter(accessTokenConverter)
                .userDetailsService(userDetailsService);
    }
}

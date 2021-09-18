package com.oas.security.oauth;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.stereotype.Component;

@Component
@EnableAuthorizationServer
public class AuthozationServerConfigurer extends AuthorizationServerConfigurerAdapter {

    private final DataSource dataSource;

    private final DefaultTokenServices tokenService;

    private final ApprovalStore approvalStore;

    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthozationServerConfigurer(final DataSource dataSource, final AuthenticationManager authenticationManager,
            final DefaultTokenServices tokenService, final ApprovalStore approvalStore) {
        this.authenticationManager = authenticationManager;
        this.dataSource = dataSource;
        this.tokenService = tokenService;
        this.approvalStore = approvalStore;
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager).tokenServices(tokenService).approvalStore(approvalStore);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        // Enable /oauth/token_key URL used by resource server to validate JWT tokens
        security.tokenKeyAccess("isAuthenticated()");
        // Enable /oauth/check_token URL
        security.checkTokenAccess("isAuthenticated()");
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.jdbc(dataSource).build();
    }
}

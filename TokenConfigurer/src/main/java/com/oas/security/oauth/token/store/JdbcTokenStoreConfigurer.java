package com.oas.security.oauth.token.store;


import java.util.Arrays;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import com.oas.security.oauth.token.OasTokenEnhancer;

@Configuration
@ConditionalOnProperty(prefix = "oauth2", name = "token.store", havingValue = "jdbc", matchIfMissing = false)
public class JdbcTokenStoreConfigurer extends AbstractTokenStoreConfigurer {
    @Autowired
    public JdbcTokenStoreConfigurer(final DataSource dataSource){
        super(dataSource);
    }

    @Bean
    @Override
    public TokenStore tokenStore(){
        return new JdbcTokenStore(dataSource);
    }

    @Bean
    public TokenEnhancerChain tokenEnhancerChain(){
         final TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
         tokenEnhancerChain.setTokenEnhancers(Arrays.asList(new OasTokenEnhancer()));
         return tokenEnhancerChain;
     }

}
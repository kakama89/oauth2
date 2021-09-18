package com.oas.security.oauth.token.store;

import java.security.KeyPair;
import java.util.Arrays;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.UserAuthenticationConverter;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import com.oas.security.oauth.token.OasTokenEnhancer;
import com.oas.security.oauth.token.Oauth2Properties;
import com.oas.security.oauth.token.TokenProperties.KeyStoreConfig;

@Configuration
@ConditionalOnProperty(prefix = "oauth2", name = "token.store", havingValue = "jwt", matchIfMissing = true)
public class JwtTokenStoreConfigurer extends AbstractTokenStoreConfigurer {
    
    private final Oauth2Properties oauth2Properties;
    
    
    private final UserDetailsService userDetailsService;
    
    @Autowired
    public JwtTokenStoreConfigurer(final DataSource dataSource, final Oauth2Properties oauth2Properties, UserDetailsService userDetailsService){
        super(dataSource);
        this.oauth2Properties = oauth2Properties;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    @Override
    public TokenStore tokenStore() {
        JwtTokenStore tokenStore = new JwtTokenStore(jwtAccessTokenConverter());
//		tokenStore.setApprovalStore(approvalStore());
        return tokenStore;
    }
    

    @Bean
    @Override
    public TokenEnhancerChain tokenEnhancerChain(){
        final TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(new OasTokenEnhancer(), jwtAccessTokenConverter()));
        return tokenEnhancerChain;
    }

    @Bean
	public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        KeyStoreConfig keyStore = oauth2Properties.getToken().getKeystore();
        ClassPathResource resource = new ClassPathResource(keyStore.getKey());
        KeyStoreKeyFactory factory = new KeyStoreKeyFactory(resource, keyStore.getPassword().toCharArray());
        KeyPair keyPair = factory.getKeyPair(keyStore.getAlias());
        converter.setKeyPair(keyPair);
        ((DefaultAccessTokenConverter) converter.getAccessTokenConverter())
                .setUserTokenConverter(userAuthenticationConverter());
        return converter;
    }
    
    @Bean
    public UserAuthenticationConverter userAuthenticationConverter() {
        DefaultUserAuthenticationConverter convert = new DefaultUserAuthenticationConverter();
        convert.setUserDetailsService(userDetailsService);
        return convert;
    }
}
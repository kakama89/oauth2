package com.oas.security.oauth.token;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;

import com.oas.security.oauth.token.store.JwtTokenStoreConfigurer;

@Configuration
@Import(value =  {JwtTokenStoreConfigurer.class})
public class TokenConfigurer {
	private final Oauth2Properties oauth2Properties;
	
	private final TokenStore tokenStore;
	
	private final TokenEnhancerChain tokenEnhancer;

	@Autowired
	public TokenConfigurer(Oauth2Properties oauth2Properties,
			TokenStore tokenStore, TokenEnhancerChain tokenEnhancer){
		this.oauth2Properties = oauth2Properties;
		this.tokenStore = tokenStore;
		this.tokenEnhancer = tokenEnhancer;
	}
	
	@Bean
	public DefaultTokenServices tokenServices() {
		DefaultTokenServices tokenServices = new DefaultTokenServices();
		tokenServices.setTokenStore(tokenStore);
		TokenProperties token = oauth2Properties.getToken();
		tokenServices.setSupportRefreshToken(token.isSupportRefreshToken());
		tokenServices.setReuseRefreshToken(token.isReuseRefreshToken());
		tokenServices.setTokenEnhancer(tokenEnhancer);
		tokenServices.setAccessTokenValiditySeconds(token.getAccessTokenValidity()); 
		tokenServices.setRefreshTokenValiditySeconds(token.getRefreshTokenValidity());
		return tokenServices;
	}
}
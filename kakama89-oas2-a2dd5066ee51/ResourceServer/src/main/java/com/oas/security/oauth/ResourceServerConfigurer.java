package com.oas.security.oauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.stereotype.Component;

import com.oas.security.oauth.token.Oauth2Properties;

@Component
@EnableResourceServer
public class ResourceServerConfigurer extends ResourceServerConfigurerAdapter {

	private final DefaultTokenServices tokenService;
	
	private final Oauth2Properties oauth2Properties;
	
	@Autowired
	public ResourceServerConfigurer(final DefaultTokenServices tokenService, final Oauth2Properties oauth2Properties) {
		this.tokenService = tokenService;
		this.oauth2Properties = oauth2Properties;
	}
	
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources
		.resourceId(oauth2Properties.getResource().getId())
		.tokenServices(tokenService).stateless(true);
	}

}

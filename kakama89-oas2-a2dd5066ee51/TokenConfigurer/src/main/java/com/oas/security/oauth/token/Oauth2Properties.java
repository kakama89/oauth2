package com.oas.security.oauth.token;


import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "oauth2")
public class Oauth2Properties {
	@NotNull
	private TokenProperties token;
	
	private Resource resource;

	@Getter
	@Setter
	public static class Resource {
		private String id;
	}
}

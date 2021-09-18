package com.oas.security.oauth.token;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenProperties {
	@NotNull
	private KeyStoreConfig keystore;
	@NotNull
	private Integer accessTokenValidity;
	@NotNull
	private Integer refreshTokenValidity;
	@NotNull
	private boolean supportRefreshToken;
	@NotNull
	private boolean reuseRefreshToken;

	@NotNull
	private String store;
	
	@Getter
	@Setter
	public static final class KeyStoreConfig{
		@NotEmpty
		private String key;
		@NotEmpty
		private String alias;
		@NotEmpty
		private String password;
	}
}

package com.oas.security;

import java.util.Optional;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public final class SecurityUtils {
	private SecurityUtils() {
		throw new AssertionError();
	}

	public static UserDetails getActualUser() {
		return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
				.filter(Authentication::isAuthenticated).map(Authentication::getPrincipal)
				.map(user -> (UserDetails) user)
				.orElseThrow(() -> new AuthenticationServiceException("Authenticated require !"));
	}
}

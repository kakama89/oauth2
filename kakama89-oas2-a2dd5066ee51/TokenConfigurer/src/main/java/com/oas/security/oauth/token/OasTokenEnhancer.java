package com.oas.security.oauth.token;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

public class OasTokenEnhancer implements TokenEnhancer {
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        UserDetails principal = (UserDetails) authentication.getPrincipal();
        Map<String, Object> additionalInformation = new HashMap<>();
        List<String> authorities = principal.getAuthorities()
                .parallelStream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        additionalInformation.put("authority", authorities);
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInformation);
        return accessToken;
    }

}

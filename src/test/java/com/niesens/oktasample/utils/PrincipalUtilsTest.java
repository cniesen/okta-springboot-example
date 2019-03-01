package com.niesens.oktasample.utils;

import org.junit.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.security.Principal;
import java.util.*;

import static org.junit.Assert.assertEquals;

public class PrincipalUtilsTest {

    @Test(expected = IllegalArgumentException.class)
    public void getPreferredUsername_unsupportedPrincipal() {
        Principal principal = () -> null;

        PrincipalUtils.getPreferredUsername(principal);
    }

    @Test(expected = NoSuchElementException.class)
    public void getPreferredUsername_missing() {
        Collection<SimpleGrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("USER_ROLE"));
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("sub", "abc123");
        OAuth2User oAuth2User = new DefaultOAuth2User(authorities, attributes,"sub");
        Principal principal = new OAuth2AuthenticationToken(oAuth2User, null, "clientRegId");
        assertEquals("user@example.com", PrincipalUtils.getPreferredUsername(principal));
    }

    @Test
    public void getPreferredUsername() {
        Collection<SimpleGrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("USER_ROLE"));
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("sub", "abc123");
        attributes.put("preferred_username", "user@example.com");
        OAuth2User oAuth2User = new DefaultOAuth2User(authorities, attributes,"sub");
        Principal principal = new OAuth2AuthenticationToken(oAuth2User, null, "clientRegId");
        assertEquals("user@example.com", PrincipalUtils.getPreferredUsername(principal));
    }

}
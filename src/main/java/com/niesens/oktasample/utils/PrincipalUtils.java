package com.niesens.oktasample.utils;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.StandardClaimNames;

import java.security.Principal;
import java.util.Map;
import java.util.NoSuchElementException;

public class PrincipalUtils {

    public static String getPreferredUsername(Principal principal) {
        if (principal instanceof OAuth2AuthenticationToken) {
            Map<String, Object> attributes = ((OAuth2AuthenticationToken) principal).getPrincipal().getAttributes();
            if (attributes.containsKey(StandardClaimNames.PREFERRED_USERNAME)
                    && attributes.get(StandardClaimNames.PREFERRED_USERNAME) instanceof String) {
                return (String) attributes.get(StandardClaimNames.PREFERRED_USERNAME);
            } else {
                throw new NoSuchElementException();
            }
        }

        throw new IllegalArgumentException("Unsupported Principal type: " + principal.getClass());
    }

}

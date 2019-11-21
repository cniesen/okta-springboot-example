package com.niesens.okta.GroupAuth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import java.util.*;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private AuthConfig authConfig;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers("/**").authenticated()
                    .and()
                .oauth2Login()
                    .userInfoEndpoint().oidcUserService(new CustomOidcUserService());
    }

    private class CustomOidcUserService extends OidcUserService {
        @Override
        public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
            OidcUser user = super.loadUser(userRequest);

            @SuppressWarnings("unchecked") List<String> groups = (ArrayList<String>) user.getAttribute("groups");
            Set<GrantedAuthority> authorities = new HashSet<>(user.getAuthorities());
            if ((groups != null) && (authConfig.getRoleGroupMapping() != null)) {
                authConfig.getRoleGroupMapping().forEach((authRole, oktaGroup) -> {
                    if(groups.contains(oktaGroup)) {
                        authorities.add(new SimpleGrantedAuthority("ROLE_" + authRole));
                    }
                });
            }

            return new DefaultOidcUser(authorities, user.getIdToken());
        }
    }
}

package com.niesens.oktasample.controller;

import com.niesens.oktasample.config.RoleConfig;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@EnableGlobalMethodSecurity(prePostEnabled = true)

@EnableConfigurationProperties(RoleConfig.class)
@RestController
public class MainController {

    @RequestMapping("/")
    public String index(@AuthenticationPrincipal OidcUser user) {
        if (user == null) {
            return "Public - not authenticated";
        } else {
            return "Public - authenticated as " + user.getAttributes().get("preferred_username");
        }
    }

    @RequestMapping("/user")
    @PreAuthorize("hasAuthority(@roleConfig.getUserRoleName())")
    public String user(@AuthenticationPrincipal OidcUser user) {
        return "User - authenticated as " + user.getAttributes().get("preferred_username");
    }

    @RequestMapping("/admin")
    public String admin(@AuthenticationPrincipal OidcUser user) {
        return "Administrator - authenticated as " + user.getAttributes().get("preferred_username");
    }

}
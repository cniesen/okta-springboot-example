package com.niesens.okta.GroupAuth.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GroupAuthController {
    @RequestMapping("/")
    public String index(@AuthenticationPrincipal OidcUser user) {
        return "Hello, your authorities are: " + user.getAuthorities().toString();
    }
}

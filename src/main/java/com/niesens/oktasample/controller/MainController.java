package com.niesens.oktasample.controller;

import com.niesens.oktasample.config.RoleConfig;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;


@EnableGlobalMethodSecurity(prePostEnabled = true)

@EnableConfigurationProperties(RoleConfig.class)
@RestController
public class MainController {

    @RequestMapping("/")
    public String index(Principal principal) {
        if (principal == null) {
            return "Public - not authenticated";
        } else {
            return "Public - authenticated as " + principal.getName();
        }
    }

    @RequestMapping("/user")
    @PreAuthorize("hasAuthority(@roleConfig.getUserRoleName())")
    public String user(Principal principal) {
        return "User - authenticated as " + principal.getName();
    }

    @RequestMapping("/admin")
    public String admin(Principal principal) {
        return "Administrator - authenticated as " + principal.getName();
    }

}
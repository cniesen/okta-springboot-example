package com.niesens.okta.GroupAuth.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@ConfigurationProperties("auth")
@Component
public class AuthConfig {
    private String emailSuffix;

    private Map<String, String> roleGroupMapping;

    public String getEmailSuffix() {
        return emailSuffix;
    }

    public void setEmailSuffix(String emailSuffix) {
        this.emailSuffix = emailSuffix;
    }

    public Map<String, String> getRoleGroupMapping() {
        return roleGroupMapping;
    }

    public void setRoleGroupMapping(Map<String, String> roleGroupMapping) {
        this.roleGroupMapping = roleGroupMapping;
    }
}

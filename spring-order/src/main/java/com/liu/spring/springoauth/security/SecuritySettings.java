package com.liu.spring.springoauth.security;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix="security-config")
public class SecuritySettings {

    private String logoutSuccssurl = "/logout";
    private String permitAll = "/api";
    private String deniedPage = "/deny";
    private String urlRoles;

    public String getLogoutSuccssurl() {
        return logoutSuccssurl;
    }

    public void setLogoutSuccssurl(String logoutSuccssurl) {
        this.logoutSuccssurl = logoutSuccssurl;
    }

    public String getPermitAll() {
        return permitAll;
    }

    public void setPermitAll(String permitAll) {
        this.permitAll = permitAll;
    }

    public String getDeniedPage() {
        return deniedPage;
    }

    public void setDeniedPage(String deniedPage) {
        this.deniedPage = deniedPage;
    }

    public String getUrlRoles() {
        return urlRoles;
    }

    public void setUrlRoles(String urlRoles) {
        this.urlRoles = urlRoles;
    }
}

package com.foxminded.university.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;

public class BasePlatformAuthentication implements Authentication {

    private final UserPrincipal userPrincipal;
    private final boolean authenticated;

    public BasePlatformAuthentication(UserPrincipal userPrincipal, boolean authenticated) {
        this.userPrincipal = userPrincipal;
        this.authenticated = authenticated;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>();
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public String getDetails() {
        return "Some details";
    }

    @Override
    public UserPrincipal getPrincipal() {
        return userPrincipal;
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean b) throws IllegalArgumentException {
    }

    @Override
    public String getName() {
        return "";
    }
}
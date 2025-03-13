package com.hotelbooking.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class CustomUserDetails implements UserDetails {

    private String username;
    private String password;
    private String role;

    // Constructor to initialize the user details
    public CustomUserDetails(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Return authorities (roles) with the "ROLE_" prefix
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Account is always non-expired
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Account is always non-locked
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Credentials are always non-expired
    }

    @Override
    public boolean isEnabled() {
        return true; // Account is always enabled
    }
}

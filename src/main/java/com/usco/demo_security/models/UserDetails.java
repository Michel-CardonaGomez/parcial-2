package com.usco.demo_security.models;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


public class UserDetails implements org.springframework.security.core.userdetails.UserDetails {

    private User user;

    public UserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // No implementamos lógica de expiración de cuenta
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // No implementamos lógica de cuenta bloqueada
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // No implementamos lógica de expiración de credenciales
    }

    @Override
    public boolean isEnabled() {
        return user.isEnabled();
    }

}

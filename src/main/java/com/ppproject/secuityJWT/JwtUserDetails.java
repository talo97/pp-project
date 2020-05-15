package com.ppproject.secuityJWT;

import com.ppproject.entities.EntityUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

public class JwtUserDetails implements UserDetails {

    private String username;
    private String password;
    private Set<GrantedAuthority> authorities;

    public JwtUserDetails(EntityUser user) {
        this.username = user.getLogin();
        this.password = user.getPassword();
        this.authorities = Collections.singleton(new SimpleGrantedAuthority("ROLE_" + user.getUserGroup()));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
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
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

package com.github.sanhak.global.security;

import com.github.sanhak.user.repository.UserEntity;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

@AllArgsConstructor
public class UserAuthInfo implements UserDetails, OAuth2User {

    private final UserEntity user;
    private final String phoneNumber;
    private final String password;
    private final Map<String, Object> attributes;

    public Long getUserId() {
        return user.getId();
    }

    public String getUserPhoneNumber() {
        return user.getPhoneNumber();
    }

    @Override
    public String getUsername() {
        return phoneNumber != null ? phoneNumber : user.getPhoneNumber();
    }

    @Override
    public String getPassword() {
        return password == null ? "" : password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));
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

    // --- OAuth2User 구현 ---
    @Override
    public Map<String, Object> getAttributes() {
        return attributes == null ? Collections.emptyMap() : attributes;
    }

    @Override
    public String getName() {
        return String.valueOf(user.getId());
    }
}
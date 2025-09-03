package com.github.sanhak.auth;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

import lombok.Getter;

import java.util.Collection;
import java.util.Map;

@Getter
public class CustomOAuth2User extends DefaultOAuth2User {

    private String providerId;
    private String name;

    public CustomOAuth2User(Collection<? extends GrantedAuthority> authorities,
                            Map<String, Object> attributes,
                            String nameAttributeKey,
                            String providerId,
                            String name) {
        super(authorities, attributes, nameAttributeKey);
        this.providerId = providerId;
        this.name = name;
    }

    @Override
    public String getName() {
        return providerId;
    }
}

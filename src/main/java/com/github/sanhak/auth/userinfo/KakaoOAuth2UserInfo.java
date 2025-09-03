package com.github.sanhak.auth.userinfo;

import java.util.Map;

public class KakaoOAuth2UserInfo implements OAuth2UserInfo {

    private final Map<String, Object> attributes;

    public KakaoOAuth2UserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String getId() {
        return String.valueOf(attributes.get("id"));
    }

    @Override
    public String getName() {
        Map<String, Object> account = (Map<String, Object>) attributes.get("kakao_account");
        if (account == null) return null;

        Map<String, Object> profile = (Map<String, Object>) account.get("profile");
        if (profile != null) {
            String nickname = (String) profile.get("nickname");
            if (nickname != null && !nickname.isEmpty()) {
                return nickname;
            }
        }

        String shortId = String.valueOf(attributes.get("id")).substring(0, 5);
        return "User" + shortId;
    }
}

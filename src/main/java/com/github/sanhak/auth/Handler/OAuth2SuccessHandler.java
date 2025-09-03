package com.github.sanhak.auth.Handler;

import com.github.sanhak.auth.CustomOAuth2User;
import com.github.sanhak.global.property.OAuthProperty;
import com.github.sanhak.global.security.jwt.JwtTokenProvider;
import com.github.sanhak.user.repository.AuthType;
import com.github.sanhak.user.repository.Role;
import com.github.sanhak.user.repository.UserEntity;
import com.github.sanhak.user.repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final JwtTokenProvider jwtTokenProvider;
    private final OAuthProperty oAuthProperty;
    private final UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();

        UserEntity user = userRepository.findByProviderId(oAuth2User.getProviderId())
                .orElseGet(() -> userRepository.save(UserEntity.builder()
                        .providerId(oAuth2User.getProviderId())
                        .name(oAuth2User.getName())
                        .authType(AuthType.KAKAO)
                        .role(Role.USER)
                        .build()));

        String accessToken = jwtTokenProvider.createAccessToken(authentication);

        String redirectUrl = String.format("%s?token=%s", oAuthProperty.getSuccess(), accessToken);
        response.sendRedirect(redirectUrl);
    }
}

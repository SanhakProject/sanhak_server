package com.github.sanhak.auth.service;

import com.github.sanhak.auth.controller.dto.request.KakaoLoginRequest;
import com.github.sanhak.auth.controller.dto.request.LoginRequest;
import com.github.sanhak.auth.controller.dto.request.RefreshTokenRequest;
import com.github.sanhak.auth.controller.dto.request.SignUpRequest;
import com.github.sanhak.auth.controller.dto.response.LoginResponse;
import com.github.sanhak.auth.exception.AuthException;
import com.github.sanhak.auth.exception.AuthExceptionCode;
import com.github.sanhak.auth.repository.RefreshTokenEntity;
import com.github.sanhak.auth.repository.RefreshTokenRepository;
import com.github.sanhak.auth.userinfo.KakaoOAuth2UserInfo;
import com.github.sanhak.global.security.jwt.JwtTokenProvider;
import com.github.sanhak.user.repository.AuthType;
import com.github.sanhak.user.repository.Role;
import com.github.sanhak.user.repository.UserEntity;
import com.github.sanhak.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final RestTemplate restTemplate = new RestTemplate();

    @Transactional
    public void signUp(SignUpRequest request) {
        if (userRepository.findByPhoneNumberAndAuthType(request.getPhoneNumber(), AuthType.PHONE).isPresent()){
            throw new AuthException(AuthExceptionCode.ALREADY_REGISTERED);
        }

        UserEntity user = UserEntity.of(
                request.getPhoneNumber(),
                passwordEncoder.encode(request.getPassword()),
                request.getName(),
                AuthType.PHONE,
                Role.USER
        );

        userRepository.save(user);
    }

    @Transactional
    public LoginResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getPhoneNumber(), request.getPassword())
        );

        return generateTokenResponse(authentication);
    }

    @Transactional
    public LoginResponse refreshToken(RefreshTokenRequest request) {
        RefreshTokenEntity refreshToken = refreshTokenRepository.findByToken(request.getRefreshToken())
                .orElseThrow(() -> new AuthException(AuthExceptionCode.INVALID_REFRESH_TOKEN));

        if (refreshToken.isExpired()) {
            refreshTokenRepository.delete(refreshToken);
            throw new AuthException(AuthExceptionCode.EXPIRED_REFRESH_TOKEN);
        }

        UserEntity user = refreshToken.getUser();
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                user.getProviderId(),
                null,
                Collections.singleton(new SimpleGrantedAuthority(user.getRole().name()))
        );

        return generateTokenResponse(authentication);
    }

    @Transactional
    public LoginResponse kakaoLogin(KakaoLoginRequest request) {
        // 카카오 API를 호출하여 사용자 정보 가져오기
        Map<String, Object> userInfo = getKakaoUserInfo(request.getKakaoAccessToken());
        KakaoOAuth2UserInfo kakaoUserInfo = new KakaoOAuth2UserInfo(userInfo);

        String providerId = kakaoUserInfo.getId();
        String name = kakaoUserInfo.getName();

        // 기존 사용자 조회 또는 신규 생성
        UserEntity user = userRepository.findByProviderId(providerId)
                .orElseGet(() -> userRepository.save(UserEntity.builder()
                        .providerId(providerId)
                        .name(name)
                        .authType(AuthType.KAKAO)
                        .role(Role.USER)
                        .build()));

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                user.getProviderId(),
                null,
                Collections.singleton(new SimpleGrantedAuthority(user.getRole().name()))
        );

        return generateTokenResponse(authentication);
    }

    private LoginResponse generateTokenResponse(Authentication authentication) {
        String accessToken = jwtTokenProvider.createAccessToken(authentication);
        String refreshToken = jwtTokenProvider.createRefreshToken(authentication);

        // 기존 리프레시 토큰 삭제 (providerId로 사용자 조회)
        String providerId = authentication.getName();
        UserEntity user = userRepository.findByProviderId(providerId)
                .orElseThrow(() -> new AuthException(AuthExceptionCode.INVALID_REFRESH_TOKEN));

        refreshTokenRepository.deleteByUser(user);

        // 새로운 리프레시 토큰 저장
        LocalDateTime expiresAt = LocalDateTime.now().plusSeconds(
                jwtTokenProvider.getRefreshTokenValidityInMilliseconds() / 1000
        );

        refreshTokenRepository.save(RefreshTokenEntity.builder()
                .token(refreshToken)
                .user(user)
                .expiresAt(expiresAt)
                .build());

        return LoginResponse.of(accessToken, refreshToken);
    }

    private Map<String, Object> getKakaoUserInfo(String accessToken) {
        String url = "https://kapi.kakao.com/v2/user/me";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<Map> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    Map.class
            );
            return response.getBody();
        } catch (Exception e) {
            log.error("Failed to get Kakao user info", e);
            throw new AuthException(AuthExceptionCode.KAKAO_USER_INFO_FAILED);
        }
    }

}

package com.github.sanhak.auth.controller;

import com.github.sanhak.auth.controller.dto.request.KakaoLoginRequest;
import com.github.sanhak.auth.controller.dto.request.LoginRequest;
import com.github.sanhak.auth.controller.dto.request.RefreshTokenRequest;
import com.github.sanhak.auth.controller.dto.request.SignUpRequest;
import com.github.sanhak.auth.controller.dto.response.LoginResponse;
import com.github.sanhak.auth.service.AuthService;
import com.github.sanhak.global.dto.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Auth", description = "인증 관련 API (모바일 앱용)")
@RequiredArgsConstructor
@RequestMapping("/auth")
@RestController
public class AuthController {

    private final AuthService authService;

    @Operation(
            summary = "자체 회원가입",
            description = "전화번호와 비밀번호로 회원가입합니다. 회원가입 후 로그인 API를 호출하여 토큰을 받아야 합니다."
    )
    @PostMapping("/signup")
    public Response<Void> signup(@Valid @RequestBody SignUpRequest signUpRequest) {
        authService.signUp(signUpRequest);
        return Response.ok();
    }

    @Operation(
            summary = "자체 로그인",
            description = "전화번호와 비밀번호로 로그인하여 액세스 토큰과 리프레시 토큰을 발급받습니다."
    )
    @PostMapping("/login")
    public Response<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        return Response.ok(authService.login(loginRequest));
    }

    @Operation(
            summary = "토큰 갱신",
            description = "리프레시 토큰으로 새로운 액세스 토큰과 리프레시 토큰을 발급받습니다. 액세스 토큰이 만료되었을 때 사용합니다."
    )
    @PostMapping("/refresh")
    public Response<LoginResponse> refresh(@Valid @RequestBody RefreshTokenRequest request) {
        return Response.ok(authService.refreshToken(request));
    }

    @Operation(
            summary = "카카오 로그인 (모바일용)",
            description = "모바일 앱의 카카오 SDK에서 발급받은 액세스 토큰으로 로그인합니다. 백엔드 JWT 토큰을 발급받습니다."
    )
    @PostMapping("/oauth2/kakao")
    public Response<LoginResponse> kakaoLogin(@Valid @RequestBody KakaoLoginRequest request) {
        return Response.ok(authService.kakaoLogin(request));
    }
}

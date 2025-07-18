package com.github.sanhak.auth.controller;

import com.github.sanhak.auth.controller.dto.request.LoginRequest;
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
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Auth")
@RequiredArgsConstructor
@RestController
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "자체 회원가입")
    @PostMapping("/auth/signup")
    public Response<Void> signup(@Valid @RequestBody SignUpRequest signUpRequest) {
        authService.signUp(signUpRequest);
        return Response.ok();
    }

    @Operation(summary = "자체 로그인")
    @PostMapping("/auth/login")
    public Response<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        return Response.ok(authService.login(loginRequest));
    }
}

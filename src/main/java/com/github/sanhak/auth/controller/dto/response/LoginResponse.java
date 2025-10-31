package com.github.sanhak.auth.controller.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(staticName = "of")
public class LoginResponse {

    @Schema(description = "액세스 토큰 (Authorization: Bearer {accessToken})")
    String accessToken;

    @Schema(description = "리프레시 토큰 (토큰 갱신용)")
    String refreshToken;
}

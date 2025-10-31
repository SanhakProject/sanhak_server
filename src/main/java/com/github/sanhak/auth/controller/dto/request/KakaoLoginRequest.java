package com.github.sanhak.auth.controller.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class KakaoLoginRequest {

    @Schema(description = "카카오 액세스 토큰 (모바일 SDK에서 발급받은 토큰)", example = "ya29.a0AfH6SMBx...")
    @NotBlank(message = "카카오 액세스 토큰은 필수입니다.")
    String kakaoAccessToken;
}

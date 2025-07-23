package com.github.sanhak.auth.controller.dto.request;

import com.github.sanhak.global.regexp.RegExps;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor(staticName = "of")
public class SignUpRequest {

    @Schema(description = "전화번호", example = "010-1234-5678")
    @NotBlank(message = "전화번호는 필수입니다.")
    @Pattern(regexp = RegExps.RegPHONE_NUMBER, message = RegExps.PHONE_NUMBER_MESSAGE)
    String phoneNumber;

    @Schema(description = "비밀번호", example = "P@ssw0rd123!")
    @NotBlank(message = "비밀번호는 필수입니다.")
    @Size(min = 4, message = "비밀번호는 최소 4자리 이상이어야 합니다.")
    String password;

    @Schema(description = "사용자명", example = "홍길동")
    @NotBlank(message = "사용자명은 필수입니다.")
    @Pattern(regexp = RegExps.NAME_EXPRESSION, message = RegExps.NAME_MESSAGE)
    @Size(max = 10, message = "사용자명은 10자 이하로 입력해주세요.")
    String name;
}

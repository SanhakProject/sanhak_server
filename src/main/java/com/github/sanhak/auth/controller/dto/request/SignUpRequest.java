package com.github.sanhak.auth.controller.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static com.github.sanhak.global.regexp.RegExps.PHONE_NUMBER;

@Getter
@AllArgsConstructor(staticName = "of")
public class SignUpRequest {

    @NotBlank
    @Pattern(regexp = PHONE_NUMBER)
    String phoneNumber;

    @NotBlank
    String password;

    @NotBlank
    String name;
}

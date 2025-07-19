package com.github.sanhak.auth.controller.dto.request;

import com.github.sanhak.global.regexp.RegExps;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class LoginRequest {

    @NotBlank
    @Pattern(regexp = RegExps.RegPHONE_NUMBER, message = RegExps.PHONE_NUMBER_MESSAGE)
    String phoneNumber;

    @NotBlank
    String password;

}

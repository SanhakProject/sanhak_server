package com.github.sanhak.auth.exception;

import com.github.sanhak.global.exception.CustomExceptionCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AuthExceptionCode implements CustomExceptionCode {

    ALREADY_REGISTERED("이미 가입된 사용자입니다.", 1004),
    ;

    private final String message;
    private final int status;
}

package com.github.sanhak.global.security.jwt.exception;

import com.github.sanhak.global.exception.CustomExceptionCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum JwtAuthenticationExceptionCode implements CustomExceptionCode {

    MISSING_AUTHORITY("권한 정보가 없는 토큰입니다", 1000),
    TOKEN_MISSING("토큰이 존재하지 않습니다.", 1001),
    INVALID_TOKEN("유효하지 않은 토큰입니다.", 1002);

    private final String message;
    private final int status;
}

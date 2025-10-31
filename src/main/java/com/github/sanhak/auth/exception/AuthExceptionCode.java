package com.github.sanhak.auth.exception;

import com.github.sanhak.global.exception.CustomExceptionCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AuthExceptionCode implements CustomExceptionCode {

    ALREADY_REGISTERED("이미 가입된 사용자입니다.", 1001),
    INVALID_REFRESH_TOKEN("유효하지 않은 리프레시 토큰입니다.", 1002),
    EXPIRED_REFRESH_TOKEN("만료된 리프레시 토큰입니다.", 1003),
    KAKAO_USER_INFO_FAILED("카카오 사용자 정보를 가져올 수 없습니다.", 1004),
    ;

    private final String message;
    private final int status;
}

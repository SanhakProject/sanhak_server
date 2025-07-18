package com.github.sanhak.user.exception;

import com.github.sanhak.global.exception.CustomExceptionCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserExceptionCode implements CustomExceptionCode {

    USER_NOT_FOUND("유저가 존재하지 않습니다.", 1003),
    ;

    private final String message;
    private final int status;
}

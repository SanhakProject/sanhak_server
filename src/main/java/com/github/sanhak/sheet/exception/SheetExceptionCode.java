package com.github.sanhak.sheet.exception;

import com.github.sanhak.global.exception.CustomExceptionCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SheetExceptionCode implements CustomExceptionCode {

    SHEET_NOT_FOUND("악보를 찾을 수 없습니다.", 3001),
    ;

    private final String message;
    private final int status;
}

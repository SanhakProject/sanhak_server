package com.github.sanhak.playresult.exception;

import com.github.sanhak.global.exception.CustomExceptionCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PlayResultExceptionCode implements CustomExceptionCode {
    INVALID_TOTAL("전체 노트 수(total)는 성공 노트 수(success)와 실패 노트 수(fail)의 합과 같아야 합니다.", 5001),
    INVALID_MAX_COMBO("최대 콤보(maxCombo)는 성공 노트 수(success)보다 클 수 없습니다.", 5002),
    ZERO_TOTAL("전체 노트 수(total)는 0일 수 없습니다. 최소 1 이상의 값이어야 합니다.", 5003),
    INVALID_FIRST_FAILED_MEASURE("첫 번째로 틀린 마디 번호가 올바르지 않습니다.", 5004),
    INVALID_FIRST_FAILED_SLOT("첫 번째로 틀린 슬롯 번호가 올바르지 않습니다.", 5005);

    private final String message;
    private final int status;
}

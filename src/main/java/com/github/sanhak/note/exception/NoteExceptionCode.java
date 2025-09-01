package com.github.sanhak.note.exception;

import com.github.sanhak.global.exception.CustomExceptionCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum NoteExceptionCode implements CustomExceptionCode {
    MEASURE_OR_NOTE_EMPTY("악보에 마디 또는 노트 정보가 없습니다.", 4001),
    NOTE_INDEX_OUT_OF_RANGE("노트 위치가 허용된 범위를 벗어났습니다. (마디 또는 슬롯 값 확인)", 4002),
    LYRICS_INDEX_OUT_OF_RANGE("가사 위치가 허용된 범위를 벗어났습니다. (마디 또는 슬롯 값 확인)", 4003),
    ;

    private final String message;
    private final int status;
}

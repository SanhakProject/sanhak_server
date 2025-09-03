package com.github.sanhak.track.exception;

import com.github.sanhak.global.exception.CustomExceptionCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TrackExceptionCode implements CustomExceptionCode {

    TRACK_NOT_FOUND("트랙을 찾을 수 없습니다.", 2001),
    TRACK_AUDIO_URL_GENERATION_FAILED("트랙 오디오 URL 생성에 실패했습니다.", 2002),
    ;

    private final String message;
    private final int status;
}

package com.github.sanhak.track.exception;

import com.github.sanhak.global.exception.CustomException;

public class TrackException extends CustomException {

    public TrackException(TrackExceptionCode code) {

        super(code);
    }
}

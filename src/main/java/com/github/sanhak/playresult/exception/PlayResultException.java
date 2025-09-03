package com.github.sanhak.playresult.exception;

import com.github.sanhak.global.exception.CustomException;

public class PlayResultException extends CustomException {

    public PlayResultException(PlayResultExceptionCode code) {

        super(code);
    }
}

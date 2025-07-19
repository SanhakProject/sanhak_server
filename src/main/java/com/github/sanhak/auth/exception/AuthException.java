package com.github.sanhak.auth.exception;

import com.github.sanhak.global.exception.CustomException;

public class AuthException extends CustomException {

    public AuthException(AuthExceptionCode code) {

        super(code);
    }
}

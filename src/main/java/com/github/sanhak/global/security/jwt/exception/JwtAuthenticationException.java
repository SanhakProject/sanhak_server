package com.github.sanhak.global.security.jwt.exception;

import com.github.sanhak.global.exception.CustomException;

public class JwtAuthenticationException extends CustomException {

    public JwtAuthenticationException(JwtAuthenticationExceptionCode code) {

        super(code);
    }
}

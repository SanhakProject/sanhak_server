package com.github.sanhak.user.exception;

import com.github.sanhak.global.exception.CustomException;

public class UserException extends CustomException {

    public UserException(UserExceptionCode code) {

        super(code);
    }
}

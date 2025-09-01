package com.github.sanhak.sheet.exception;

import com.github.sanhak.global.exception.CustomException;

public class SheetException extends CustomException {

    public SheetException(SheetExceptionCode code) {

        super(code);
    }
}

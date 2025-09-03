package com.github.sanhak.note.exception;

import com.github.sanhak.global.exception.CustomException;

public class NoteException extends CustomException {

    public NoteException(NoteExceptionCode code) {

        super(code);
    }
}

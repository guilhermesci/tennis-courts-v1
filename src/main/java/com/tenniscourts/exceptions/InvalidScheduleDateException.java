package com.tenniscourts.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidScheduleDateException extends Exception {

    public InvalidScheduleDateException(String message) {
        super(message);
    }
}

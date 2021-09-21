package com.tenniscourts.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidScheduleStartDateException extends Exception {

    public InvalidScheduleStartDateException() {
        super("Schedule start date must be greater or equals than today");
    }
}

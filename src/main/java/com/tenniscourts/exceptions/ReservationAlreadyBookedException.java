package com.tenniscourts.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ReservationAlreadyBookedException extends Exception{

    public ReservationAlreadyBookedException(Long id) {
        super(String.format("Reservation already registered in the system for the schedule_id: %d", id));
    }
}

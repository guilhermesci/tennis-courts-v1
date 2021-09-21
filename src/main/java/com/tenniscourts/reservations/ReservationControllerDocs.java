package com.tenniscourts.reservations;

import com.tenniscourts.exceptions.ReservationAlreadyBookedException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.PathVariable;


@Api("Manages tennis courts reservations")
public interface ReservationControllerDocs {

    @ApiOperation(value = "Create a new reservation")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Success in reservation creation"),
            @ApiResponse(code = 400, message = "Missing required fields or incorrect values for field ranges")
    })
    ReservationDTO bookReservation(CreateReservationRequestDTO createReservationRequestDTO) throws ReservationAlreadyBookedException;

    @ApiOperation(value = "Returns a reservation found by a valid given id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success in reservation return"),
            @ApiResponse(code = 404, message = "Reservation not found with given id")
    })
    ReservationDTO findReservationById(@PathVariable Long reservationId);

    @ApiOperation(value = "Cancel a reservation by a valid given id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success in reservation cancellation"),
            @ApiResponse(code = 404, message = "Reservation not found with given id")
    })
    ReservationDTO cancelReservation(@PathVariable Long reservationId);

    @ApiOperation(value = "Reschedule a reservation by a valid given reservation id and new schedule id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success in reservation rescheduling"),
            @ApiResponse(code = 404, message = "Reservation not found with given id")
    })
    ReservationDTO rescheduleReservation(@PathVariable Long reservationId, @PathVariable Long scheduleId) throws ReservationAlreadyBookedException;
}

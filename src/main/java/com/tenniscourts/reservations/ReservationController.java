package com.tenniscourts.reservations;

import com.tenniscourts.exceptions.ReservationAlreadyBookedException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v2/reservations")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ReservationController implements ReservationControllerDocs {

    private final ReservationService reservationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReservationDTO bookReservation(@RequestBody @Valid CreateReservationRequestDTO createReservationRequestDTO) throws ReservationAlreadyBookedException {
        return reservationService.bookReservation(createReservationRequestDTO);
    }

    @GetMapping("/{reservationId}")
    public ReservationDTO findReservationById(@PathVariable Long reservationId) {
        return reservationService.findReservationById(reservationId);
    }

    @PatchMapping("/{reservationId}/cancel")
    public ReservationDTO cancelReservation(@PathVariable Long reservationId) {
        return reservationService.cancelReservation(reservationId);
    }

    @PatchMapping("/{reservationId}/reschedule/{newScheduleId}")
    public ReservationDTO rescheduleReservation(@PathVariable Long reservationId, @PathVariable Long newScheduleId) throws ReservationAlreadyBookedException {
        return reservationService.rescheduleReservation(reservationId, newScheduleId);
    }
}

package com.tenniscourts.reservations;

import com.tenniscourts.exceptions.EntityNotFoundException;
import com.tenniscourts.exceptions.ReservationAlreadyBookedException;
import com.tenniscourts.guests.GuestService;
import com.tenniscourts.schedules.ScheduleService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.TreeMap;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationMapper reservationMapper;
    private final ScheduleService scheduleService;
    private final GuestService guestService;

    public ReservationDTO bookReservation(CreateReservationRequestDTO createReservationRequestDTO) throws ReservationAlreadyBookedException {
        guestService.findGuestById(createReservationRequestDTO.getGuestId());
        scheduleService.findScheduleById(createReservationRequestDTO.getScheduleId());
        verifyIfExistsReservationReadyToPlayWithGivenScheduleId(createReservationRequestDTO.getScheduleId());

        Reservation reservation = reservationMapper.map(createReservationRequestDTO);
        reservation.setValue(BigDecimal.valueOf(10));
        Reservation savedReservation = reservationRepository.save(reservation);

        return reservationMapper.map(savedReservation);
    }

    private void verifyIfExistsReservationReadyToPlayWithGivenScheduleId(Long scheduleId) throws ReservationAlreadyBookedException {
        Optional<Reservation> optSavedReservation = reservationRepository.findByScheduleIdAndReservationStatus(scheduleId,ReservationStatus.READY_TO_PLAY);
        if (optSavedReservation.isPresent()) {
            throw new ReservationAlreadyBookedException(scheduleId);
        }
    }

    public ReservationDTO findReservationById(Long reservationId) {
        return reservationRepository.findById(reservationId).map(reservationMapper::map).orElseThrow(() -> {
            throw new EntityNotFoundException("Reservation not found.");
        });
    }

    public ReservationDTO cancelReservation(Long reservationId) {
        return reservationMapper.map(this.cancel(reservationId));
    }

    private Reservation cancel(Long reservationId) {
        return reservationRepository.findById(reservationId).map(reservation -> {

            this.validateCancellation(reservation);

            BigDecimal refundValue = getRefundValue(reservation);
            return this.updateReservation(reservation, refundValue, ReservationStatus.CANCELLED);

        }).orElseThrow(() -> {
            throw new EntityNotFoundException("Reservation not found.");
        });
    }

    private Reservation updateReservation(Reservation reservation, BigDecimal refundValue, ReservationStatus status) {
        reservation.setReservationStatus(status);
        reservation.setValue(reservation.getValue().subtract(refundValue));
        reservation.setRefundValue(refundValue);

        return reservationRepository.save(reservation);
    }

    private void validateCancellation(Reservation reservation) {
        if (!ReservationStatus.READY_TO_PLAY.equals(reservation.getReservationStatus())) {
            throw new IllegalArgumentException("Cannot cancel/reschedule because it's not in ready to play status.");
        }

        if (reservation.getSchedule().getStartDateTime().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Can cancel/reschedule only future dates.");
        }
    }

    public BigDecimal getRefundValue(Reservation reservation) {
        long minutes = ChronoUnit.MINUTES.between(LocalDateTime.now(), reservation.getSchedule().getStartDateTime());

        BigDecimal feePercentage = getFeePercentage(minutes);

        return reservation.getValue().subtract(reservation.getValue().multiply(feePercentage));
    }

    private BigDecimal getFeePercentage(Long minutes){
        TreeMap<Long, BigDecimal> feeMap = new TreeMap<>();

        feeMap.put(0L, BigDecimal.valueOf(1));      // 0min     | 00:00         -> 100%
        feeMap.put(1L, BigDecimal.valueOf(0.75));   // 1min     | 00:01 - 01:59 -> 75%
        feeMap.put(120L, BigDecimal.valueOf(0.5));  // 120min   | 02:00 - 11:59 -> 50%
        feeMap.put(720L, BigDecimal.valueOf(0.25)); // 720min   | 12:00 - 23:59 -> 25%
        feeMap.put(1440L, BigDecimal.ZERO);         // 1440min  | 24:00+        -> 0%

        return feeMap.floorEntry(minutes).getValue();
    }

    public ReservationDTO rescheduleReservation(Long previousReservationId, Long newScheduleId) throws ReservationAlreadyBookedException {
        verifyIfRescheduleIsValid(previousReservationId, newScheduleId);

        //Cancel previous reservation and update others attributes such as refundValue
        Reservation previousReservation = cancel(previousReservationId);
        //Set the proper status for previousReservation
        previousReservation.setReservationStatus(ReservationStatus.RESCHEDULED);
        reservationRepository.save(previousReservation);

        //Create a new reservation
        ReservationDTO newReservation = bookReservation(CreateReservationRequestDTO.builder()
                .guestId(previousReservation.getGuest().getId())
                .scheduleId(newScheduleId)
                .build());
        newReservation.setPreviousReservation(reservationMapper.map(previousReservation));
        return newReservation;
    }

    public void verifyIfRescheduleIsValid(Long previousReservationId, Long newScheduleId) throws ReservationAlreadyBookedException {
        //Check rescheduling attempt by selecting same slot
        ReservationDTO previousReservationDTO = findReservationById(previousReservationId);
        if (newScheduleId.equals(previousReservationDTO.getSchedule().getId())) {
            throw new IllegalArgumentException("Cannot reschedule to the same slot.");
        }

        //Check rescheduling attempt by selecting a schedule that has already been used
        CreateReservationRequestDTO createReservationRequestDTO = CreateReservationRequestDTO.builder()
                .guestId(previousReservationDTO.getGuestId())
                .scheduleId(newScheduleId).build();
        verifyIfExistsReservationReadyToPlayWithGivenScheduleId(createReservationRequestDTO.getScheduleId());
    }
}

package com.tenniscourts.reservations;

import com.tenniscourts.config.persistence.BaseEntity;
import com.tenniscourts.guests.Guest;
import com.tenniscourts.schedules.Schedule;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;


@Entity
@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Reservation extends BaseEntity<Long> {

    @OneToOne
    private Guest guest;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    private Schedule schedule;

    @NotNull
    private BigDecimal value;

    @NotNull
    private ReservationStatus reservationStatus = ReservationStatus.READY_TO_PLAY;

    private BigDecimal refundValue;

    public Reservation(){
    }

    @Builder
    public Reservation(Long id, Guest guest, Schedule schedule, BigDecimal value,
                       ReservationStatus reservationStatus, BigDecimal refundValue) {
        super(id);
        this.guest = guest;
        this.schedule = schedule;
        this.value = value;
        this.reservationStatus = reservationStatus;
        this.refundValue = refundValue;
    }
}

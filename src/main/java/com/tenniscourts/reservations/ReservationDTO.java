package com.tenniscourts.reservations;

import com.tenniscourts.schedules.ScheduleDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDTO {

    private Long id;

    private ScheduleDTO schedule;

    private String reservationStatus;

    private ReservationDTO previousReservation;

    private BigDecimal refundValue;

    private BigDecimal value;

    @NotNull
    @ApiModelProperty(required = true)
    private Long scheduledId;

    @NotNull
    @ApiModelProperty(required = true)
    private Long guestId;
}

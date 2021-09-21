package com.tenniscourts.reservations;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateReservationRequestDTO {

    @NotNull
    @ApiModelProperty(required = true)
    private Long guestId;

    @NotNull
    @ApiModelProperty(required = true)
    private Long scheduleId;

}

package com.tenniscourts.schedules;

import com.tenniscourts.tenniscourts.TennisCourtDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleDTO {

    private Long id;

    private TennisCourtDTO tennisCourt;

    @NotNull
    @ApiModelProperty(required = true)
    private Long tennisCourtId;

    @NotNull
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm")
    @ApiModelProperty(required = true, example = "2021-09-20'T'20:00")
    private LocalDateTime startDateTime;

    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm")
    private LocalDateTime endDateTime;

}

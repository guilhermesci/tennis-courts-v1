package com.tenniscourts.tenniscourts;

import com.tenniscourts.schedules.ScheduleDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TennisCourtDTO {

    private Long id;

    @NotNull
    @ApiModelProperty(required = true)
    private String name;

    private List<ScheduleDTO> tennisCourtSchedules;

}

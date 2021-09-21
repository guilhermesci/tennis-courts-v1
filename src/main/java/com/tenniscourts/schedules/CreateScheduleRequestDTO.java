package com.tenniscourts.schedules;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateScheduleRequestDTO {

    @NotNull
    private Long tennisCourtId;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @NotNull(message = "Start Date Time can not be null and must be in a yyyy-MM-dd'T'HH:mm format")
    private LocalDateTime startDateTime;

}

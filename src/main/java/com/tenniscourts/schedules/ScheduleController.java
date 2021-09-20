package com.tenniscourts.schedules;

import com.tenniscourts.config.BaseRestController;
import com.tenniscourts.reservations.ReservationControllerDocs;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/api/v2/schedules")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ScheduleController extends BaseRestController implements ScheduleControllerDocs {

    private final ScheduleService scheduleService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ScheduleDTO addScheduleTennisCourt(@RequestBody @Valid CreateScheduleRequestDTO createScheduleRequestDTO) {
        return scheduleService.addSchedule(createScheduleRequestDTO.getTennisCourtId(), createScheduleRequestDTO);
    }

    @GetMapping("/{startDate}/{endDate}")
    public List<ScheduleDTO> findSchedulesByDates(@PathVariable LocalDate startDate, @PathVariable LocalDate endDate) {
        return scheduleService.findSchedulesByDates(LocalDateTime.of(startDate, LocalTime.of(0, 0)),
                LocalDateTime.of(endDate, LocalTime.of(23, 59)));
    }

    @GetMapping("/{scheduleId}")
    public ScheduleDTO findScheduleById(@PathVariable Long scheduleId) {
        return scheduleService.findScheduleById(scheduleId);
    }
}

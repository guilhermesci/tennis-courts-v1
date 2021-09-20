package com.tenniscourts.schedules;

import com.tenniscourts.schedules.CreateScheduleRequestDTO;
import com.tenniscourts.schedules.ScheduleDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.util.List;


@Api("Manages tennis courts schedule")
public interface ScheduleControllerDocs {

    @ApiOperation(value = "Add a new schedule for a tennis court")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Success in schedule creation"),
            @ApiResponse(code = 400, message = "Missing required fields or incorrect values for field ranges")
    })
    ScheduleDTO addScheduleTennisCourt(CreateScheduleRequestDTO createScheduleRequestDTO);

    @ApiOperation(value = "Returns a list of schedules found by a range of valid given dates")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success in schedules return"),
            @ApiResponse(code = 404, message = "Schedules not found with given dates")
    })
    List<ScheduleDTO> findSchedulesByDates(@PathVariable LocalDate startDate, @PathVariable LocalDate endDate);

    @ApiOperation(value = "Return a schedule by a valid given id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success in schedule return"),
            @ApiResponse(code = 404, message = "Schedule not found with given id")
    })
    ScheduleDTO findScheduleById(@PathVariable Long scheduleId);
}

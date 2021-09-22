package com.tenniscourts.schedules;

import com.tenniscourts.exceptions.InvalidScheduleDateException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.util.List;


@Api("Manages tennis courts schedule")
public interface ScheduleControllerDocs {

    @ApiOperation(value = "Add a new schedule for a tennis court")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Success in tennis court schedule creation"),
            @ApiResponse(code = 400, message = "Missing required fields or incorrect values for field ranges")
    })
    ScheduleDTO addScheduleTennisCourt(CreateScheduleRequestDTO createScheduleRequestDTO) throws InvalidScheduleDateException;

    @ApiOperation(value = "Returns a list of schedules found by a range of valid given dates")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success in schedules list return"),
            @ApiResponse(code = 404, message = "Invalid range of given dates")
    })
    List<ScheduleDTO> findSchedulesByDates(@PathVariable LocalDate startDate, @PathVariable LocalDate endDate) throws InvalidScheduleDateException;

    @ApiOperation(value = "Return a schedule by a valid given id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success in schedule return"),
            @ApiResponse(code = 404, message = "Schedule not found with given id")
    })
    ScheduleDTO findScheduleById(@PathVariable Long scheduleId);

    @ApiOperation(value = "Return a list of schedules by a valid given tennis court id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success in tennis court's schedule list return"),
            @ApiResponse(code = 404, message = "Tennis court not found with given tennis court id")
    })
    List<ScheduleDTO> findSchedulesByTennisCourtId(@PathVariable Long tennisCourtId);

    @ApiOperation(value = "Returns a list of free schedules found by a valid given date")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success in schedules list return"),
            @ApiResponse(code = 404, message = "Invalid given date")
    })
    List<ScheduleDTO> findFreeSchedulesByDate(@PathVariable LocalDate date) throws InvalidScheduleDateException;
}

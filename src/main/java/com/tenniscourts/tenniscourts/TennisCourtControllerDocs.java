package com.tenniscourts.tenniscourts;

import com.tenniscourts.tenniscourts.TennisCourtDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.PathVariable;


@Api("Manages tennis courts")
public interface TennisCourtControllerDocs {

    @ApiOperation(value = "Create a new tennis court")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Success in tennis court creation"),
            @ApiResponse(code = 400, message = "Missing required fields or incorrect values for field ranges")
    })
    TennisCourtDTO addTennisCourt(TennisCourtDTO tennisCourtDTO);

    @ApiOperation(value = "Returns a tennis court found by a valid given id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success in tennis court return"),
            @ApiResponse(code = 404, message = "Tennis court not found with given id")
    })
    TennisCourtDTO findTennisCourtById(@PathVariable Long tennisCourtId);

    @ApiOperation(value = "Returns a tennis court with schedules found by a valid given id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success in tennis court return"),
            @ApiResponse(code = 404, message = "Reservation not found with given id")
    })
    TennisCourtDTO findTennisCourtWithSchedulesById(@PathVariable Long tennisCourtId);
}

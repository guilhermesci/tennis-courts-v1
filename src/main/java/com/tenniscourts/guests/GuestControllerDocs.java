package com.tenniscourts.guests;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;


@Api("Manages guests")
public interface GuestControllerDocs {

    @ApiOperation(value = "Create a new guest")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Success in guest creation"),
            @ApiResponse(code = 400, message = "Missing required fields or incorrect values for field ranges")
    })
    GuestDTO addGuest(GuestDTO guestDTO);

    @ApiOperation(value = "Returns a list of all guests")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success in the list return")
    })
    List<GuestDTO> findAll();

    @ApiOperation(value = "Returns a guest by a valid given id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success in guest return"),
            @ApiResponse(code = 404, message = "Guest not found with given id")
    })
    GuestDTO findGuestById(@PathVariable Long guestId);

    @ApiOperation(value = "Returns a guest by a valid given name")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success in guest return"),
            @ApiResponse(code = 404, message = "Guest not found with given name")
    })
    GuestDTO findGuestByName(@PathVariable String guestName);

    @ApiOperation(value = "Delete a guest by a valid given id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success in guest delete"),
            @ApiResponse(code = 404, message = "Guest not found with given id")
    })
    void deleteGuestById(@PathVariable Long guestId);

    @ApiOperation(value = "Update guest by a valid given id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success in update guest"),
            @ApiResponse(code = 404, message = "Guest not found with given id")
    })
    GuestDTO updateGuestById(@PathVariable Long guestId, GuestDTO guestDTO);
}

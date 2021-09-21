package com.tenniscourts.guests;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v2/guests")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class GuestController implements GuestControllerDocs {

    private final GuestService guestService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GuestDTO addGuest(@RequestBody @Valid GuestDTO guestDTO) {
        return guestService.addGuest(guestDTO);
    }

    @GetMapping("/all")
    public List<GuestDTO> findAll() {
        return guestService.findAll();
    }

    @GetMapping("/{guestId}")
    public GuestDTO findGuestById(@PathVariable Long guestId) {
        return guestService.findGuestById(guestId);
    }

    @GetMapping
    public GuestDTO findGuestByName(@RequestParam(value = "guestName") String guestName) {
        return guestService.findGuestByName(guestName);
    }

    @DeleteMapping("/{guestId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGuestById(@PathVariable Long guestId) {
        guestService.deleteGuestById(guestId);
    }

    @PatchMapping("/{guestId}")
    public GuestDTO updateGuestById(@PathVariable Long guestId, @RequestBody @Valid GuestDTO guestDTO) {
        return guestService.updateGuestById(guestId, guestDTO);
    }
}

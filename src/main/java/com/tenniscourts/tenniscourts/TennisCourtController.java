package com.tenniscourts.tenniscourts;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v2/tennis-courts")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class TennisCourtController implements TennisCourtControllerDocs {

    private final TennisCourtService tennisCourtService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TennisCourtDTO addTennisCourt(@RequestBody @Valid TennisCourtDTO tennisCourtDTO) {
        return tennisCourtService.addTennisCourt(tennisCourtDTO);
    }

    @GetMapping("/{tennisCourtId}")
    public TennisCourtDTO findTennisCourtById(@PathVariable Long tennisCourtId) {
        return tennisCourtService.findTennisCourtById(tennisCourtId);
    }

    @GetMapping("/{tennisCourtId}/with-schedule")
    public TennisCourtDTO findTennisCourtWithSchedulesById(@PathVariable Long tennisCourtId) {
        return tennisCourtService.findTennisCourtWithSchedulesById(tennisCourtId);
    }
}

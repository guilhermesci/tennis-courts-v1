package com.tenniscourts.guests;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GuestDTO {

    private Long id;

    @NotNull
    @ApiModelProperty(required = true)
    private String name;
}

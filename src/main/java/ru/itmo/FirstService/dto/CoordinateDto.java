package ru.itmo.FirstService.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class CoordinateDto {
    private float x;

    @NotNull(message = "y can't be null")
    private Integer y;
}

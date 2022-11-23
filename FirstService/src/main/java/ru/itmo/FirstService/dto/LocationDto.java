package ru.itmo.FirstService.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class LocationDto {

    private int x;

    private float y;

    @NotNull(message = "z can't be null")
    private Long z;

    @NotNull(message = "name can't be null")
    private String name;
}

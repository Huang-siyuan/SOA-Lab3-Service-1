package ru.itmo.FirstService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class RouteDto implements Serializable {

    private String name;

    private Integer coordinatesId;

    private Integer fromId;

    private Integer toId;

    private Integer distance;
}

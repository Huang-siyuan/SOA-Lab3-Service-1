package ru.itmo.FirstService.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import ru.itmo.FirstService.entity.Route;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class MessageDto implements Serializable {

    private Route route;
    private String message;

}

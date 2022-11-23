package ru.itmo.FirstService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaginationDto implements Serializable {
    private Integer pageNumber;
    private Integer pageSize;
    private Map<String, String> orders;

    public PaginationDto(Integer pageNumber, Integer pageSize) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
    }


}

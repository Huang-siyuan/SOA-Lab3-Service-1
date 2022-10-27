package dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotBlank;

import java.io.Serializable;



@Data
@NoArgsConstructor
public class RouteDto implements Serializable {
    @NotBlank(message = "name can't be null or empty")
    private String name;

    @NotNull(message = "coordinates can't be null")
    private CoordinateDto coordinates;

    private LocationDto from;

    private LocationDto to;

    @Min(value = 2, message = "distance must be > 1")
    private int distance;
}

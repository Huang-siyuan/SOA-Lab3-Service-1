package ru.itmo.FirstService.validator;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import ru.itmo.FirstService.dao.CoordinatesDao;
import ru.itmo.FirstService.dao.LocationDao;
import ru.itmo.FirstService.dto.RouteDto;


@RequiredArgsConstructor
@Component
public class RouteValidator {

    private final CoordinatesDao coordinatesDao;

    private final LocationDao locationDao;

    public String validate(RouteDto routeDto) {
        if(routeDto.getName() == null || routeDto.getName().length() == 0) {
            return "Invalid name";
        }
        if(routeDto.getCoordinatesId() == null || coordinatesDao.findById(routeDto.getCoordinatesId()) == null) {
            return "Invalid coordinates id";
        }
        if(routeDto.getFromId() != null && locationDao.findById(routeDto.getFromId()) == null) {
            return "Invalid from location id";
        }
        if(routeDto.getToId() != null && locationDao.findById(routeDto.getToId()) == null) {
            return "Invalid to location id";
        }
        if(routeDto.getDistance() != null && routeDto.getDistance() < 1) {
            return "Invalid distance";
        }
        return null;
    }
}

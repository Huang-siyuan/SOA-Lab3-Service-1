package ru.itmo.FirstService.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itmo.FirstService.dao.RouteDao;
import ru.itmo.FirstService.dto.FilterDto;
import ru.itmo.FirstService.dto.MessageDto;
import ru.itmo.FirstService.dto.PaginationDto;
import ru.itmo.FirstService.dto.RouteDto;
import ru.itmo.FirstService.entity.Route;
import ru.itmo.FirstService.validator.RouteValidator;

import javax.ws.rs.Consumes;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping(value = "/routes")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class RouteController {

    private final RouteDao routeDao;
    private final RouteValidator routeValidator;

    @PostMapping("/filter")
    public ResponseEntity getRoutes(@RequestBody PaginationDto paginationDto) {
        List routes = routeDao.getRoutes(paginationDto.getPageNumber(), paginationDto.getPageSize(),
                paginationDto.getOrders(), paginationDto.getFilters());
        if(routes == null) {
            return ResponseEntity.status(400).body(routes);
        }
        if(routes.isEmpty()) {
            return ResponseEntity.status(404).body(routes);
        }
        return ResponseEntity.status(200).body(routes);
    }


    @PostMapping("/count")
    public ResponseEntity getRoutesCount(@RequestBody FilterDto filterDto) {
        return ResponseEntity.status(200).body(routeDao.getRoutesCount(filterDto));
    }


    @GetMapping("/{id}")
    public ResponseEntity findRouteById(@PathVariable("id") long id) {
        if(routeDao.findById(id) == null) {
            return ResponseEntity.status(404).body(null);
        }
        return ResponseEntity.status(200).body(routeDao.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteRouteById(@PathVariable("id") long id) {
        if(routeDao.deleteById(id) == -1) {
            return ResponseEntity.status(404).body(-1);
        }
        return ResponseEntity.status(200).body(0);
    }

    @PostMapping("/")
    public ResponseEntity<MessageDto> createRoute(@RequestBody RouteDto routeDto) {
        String validationMessage = routeValidator.validate(routeDto);
        if(validationMessage == null) {
            return ResponseEntity.status(201).body(new MessageDto(routeDao.save(routeDto), null));
        }
        return ResponseEntity.status(400).body(new MessageDto(null, validationMessage));
    }

    @PostMapping("/distances/sum")
    public ResponseEntity getRouteSumDistance() {
        Long sum = routeDao.getSum();
        if(sum == 0) {
            return ResponseEntity.status(404).body(0);
        }
        return ResponseEntity.status(200).body(sum);
    }


    @PostMapping("/distances/less/{value}")
    public ResponseEntity getLessDistances(@PathVariable("value") int value) {
        Long count = routeDao.getLessDistancesNumber(value);
        if(count == 0) {
            return ResponseEntity.status(404).body(0);
        }
        return ResponseEntity.status(200).body(count);
    }


    @PostMapping("/distances/unique")
    public ResponseEntity getUniqueDistances(@RequestParam("pageSize") int pageSize, @RequestParam("pageNumber") int pageNumber) {
        if(pageSize < 1 || pageNumber < 1) {
            return ResponseEntity.status(404).body(Collections.emptyList());
        }
        return ResponseEntity.status(200).body(routeDao.getUniqueDistances(pageSize, pageNumber));
    }

    @PostMapping("/distances/unique/count")
    public ResponseEntity getUniqueDistancesCount() {
        return ResponseEntity.status(200).body(routeDao.getUniqueDistancesCount());
    }


    @PutMapping("/{id}")
    public ResponseEntity updateRoute(@PathVariable("id") long id, @RequestBody RouteDto routeDto) {
        if(routeDao.findById(id) == null) {
            return ResponseEntity.status(404).body(new MessageDto(null, "Invalid id"));
        }
        String validationMessage = routeValidator.validate(routeDto);
        if(validationMessage == null &&
                routeDao.findById(id) != null) {
            Route route = routeDao.update(id, routeDto);
            return ResponseEntity.status(200).body(new MessageDto(route, null));
        }
        return ResponseEntity.status(400).body(new MessageDto(null, validationMessage));
    }



}
package ru.itmo.FirstService.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itmo.FirstService.dao.CoordinatesDao;
import ru.itmo.FirstService.dao.LocationDao;
import ru.itmo.FirstService.dao.RouteDao;
import ru.itmo.FirstService.entity.Coordinates;
import ru.itmo.FirstService.entity.Location;
import ru.itmo.FirstService.entity.Route;

import java.util.UUID;

@RestController
@RequestMapping(value = "/navigator")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class NavigatorController {

    private final LocationDao locationDao;
    private final CoordinatesDao coordinatesDao;
    private final RouteDao routeDao;


    @PostMapping("/routes/{id-from}/{id-to}")
    public ResponseEntity addRoute(@PathVariable("id-from") Integer idFrom,
                                   @PathVariable("id-to") Integer idTo) {
        System.out.println("here");
        String defaultRouteName = UUID.randomUUID().toString().replace("-", "").toLowerCase(); // Cause the name shouldn't be null. But we don't need to know the name.
        Integer distance = (int) (Math.random() * 50);
        Location locationFrom = locationDao.findById(idFrom);
        Location locationTo = locationDao.findById(idTo);
        if (locationFrom == null || locationTo == null) {
            return ResponseEntity.status(400).body(null);
        }

        Coordinates coordinates = new Coordinates((float) (4 * Math.random()), 4);
        coordinatesDao.save(coordinates);
        Route route = new Route(defaultRouteName, coordinates, locationFrom, locationTo, distance); // We don't need to know the coordinates' id. So we set it to 0 as the default.
        routeDao.save(route);
        return ResponseEntity.status(201).body(route);
    }


    @PostMapping("/routes/{id-from}/{id-to}/{shortest}")
    public ResponseEntity findLongestOrShortestRoute(@PathVariable("id-from") int idFrom,
                                               @PathVariable("id-to") int idTo,
                                               @PathVariable("shortest") int shortest) {
        if(shortest != 1 && shortest != 0) {
            return null;
        }
        Route route = routeDao.findLongestOrShortestRoute(idFrom, idTo, shortest);
        if(route == null) {
            return ResponseEntity.status(404).body(null);
        }
        return ResponseEntity.status(200).body(route);
    }



}


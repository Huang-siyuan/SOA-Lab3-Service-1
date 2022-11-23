package ru.itmo.FirstService.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/routes")
public class TestController {

    @GetMapping("/test")
    public String getMessage() {
        return "test1";
    }


}
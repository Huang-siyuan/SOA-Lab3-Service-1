package ru.itmo.FirstService.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/routes")
@RequiredArgsConstructor
public class TestController {

    @Value("${server.port}")
    private String port;

    @GetMapping("/test")
    public String getMessage() {
        return "test1";
    }

    @GetMapping("/load-balance")
    public String testLoadBalance(){
        return "Here is port: " + port;
    }

}
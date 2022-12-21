package com.tests.testsapp;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @GetMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }
}

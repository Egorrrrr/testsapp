package com.tests.testsapp;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping(value = {"/", "/login"})
    public String index(Model model) {
        return "login";
    }

}
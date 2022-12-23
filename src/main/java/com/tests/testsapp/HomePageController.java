package com.tests.testsapp;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class HomePageController {

    @GetMapping("/home")
    public String home(Model model){
        return "home";
    }

    @GetMapping("/admin")
    public String admin(Model model){
        return "home";
    }
}
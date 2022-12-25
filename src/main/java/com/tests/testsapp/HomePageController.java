package com.tests.testsapp;

import com.tests.testsapp.services.AppUserDetailService;
import com.tests.testsapp.services.AppUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class HomePageController {

    @Autowired
    AppUserDetailService appUserDetailService;
    @Autowired
    AuthenticationManager authenticationManager;

    @GetMapping("/home")
    public String home(Model model){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((AppUserDetails)principal).getUsername();
        String role = ((AppUserDetails)principal).getAuthorities().toArray()[0].toString();
        model.addAttribute("name", username);
        model.addAttribute("role", role);
        return "home";
    }

    @GetMapping("/admin")
    public String admin(Model model){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String role = ((AppUserDetails)principal).getAuthorities().toArray()[0].toString();
        if(role.equals("admin") ){
            return "admin";
        }
        return "403";
    }
}
package com.tests.testsapp;

import com.tests.testsapp.services.AppUserDetailService;
import com.tests.testsapp.services.AppUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    @GetMapping("/create-test")
    public String createTest(Model model){


        List<LabelValue> questionTypes = new ArrayList<>();
        questionTypes.add(new LabelValue("q1", "RAX"));
        questionTypes.add(new LabelValue("q2", "DA"));
        questionTypes.add(new LabelValue("q3", "NEW"));

        model.addAttribute("questionTypes", questionTypes);
        return "create-test";
    }

    @RequestMapping(value = "/getType", method = RequestMethod.POST)
    public String getType(){
        return "fragments/q1";
    }

    private class LabelValue {
        public LabelValue(String value, String label) {
            this.value = value;
            this.label = label;
        }
        public String value;
        public String label;
    }
}
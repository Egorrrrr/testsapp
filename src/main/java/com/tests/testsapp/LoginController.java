package com.tests.testsapp;

import com.tests.testsapp.entities.Questions.OneAnswerQuestion;
import com.tests.testsapp.entities.Question;
import com.tests.testsapp.repos.QuestionRepository;
import com.tests.testsapp.services.AppUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
    @Autowired
    AppUserDetailService appUserDetailService;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    public QuestionRepository questionRepository;
    private boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || AnonymousAuthenticationToken.class.
                isAssignableFrom(authentication.getClass())) {
            return false;
        }
        return authentication.isAuthenticated();
    }

    @GetMapping(value = {"/", "/login"})
    public String index(Model model) {
        OneAnswerQuestion question =new OneAnswerQuestion();
        question.setConstructorPath("sdsad");
        question.setTemplatePath("sdsds");
        question.setAnswerContent("dsds");
        question.setQuestionText("dsdsdsdsds");

        if (isAuthenticated()) {
            return "redirect:home";
        }
        return "login";
    }

    @PostMapping(value = {"/register"})
    public String register(@RequestParam("reglog") String username,@RequestParam("regpass") String password ) {
        if(username.trim().length() < 5 || password.trim().length() < 5) {
            return "redirect:login?valid_error";
        }
        if(appUserDetailService.makeNewUser(username, password)){
            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);

            Authentication authentication = authenticationManager.authenticate(authRequest);
            SecurityContext securityContext = SecurityContextHolder.getContext();
            securityContext.setAuthentication(authentication);

            return "redirect:home";
        }
        return "redirect:login?uniq_error";

    }



}
package com.tests.testsapp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tests.testsapp.entities.Question;
import com.tests.testsapp.entities.json.RawQuestion;
import com.tests.testsapp.entities.json.RawTest;
import com.tests.testsapp.repos.QuestionRepository;
import com.tests.testsapp.services.AppUserDetailService;
import com.tests.testsapp.services.AppUserDetails;
import com.tests.testsapp.services.ClassAccessorService;
import org.json.JSONException;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
public class HomePageController {

    @Autowired
    AppUserDetailService appUserDetailService;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    ClassAccessorService classAccessorService;
    @Autowired
    public QuestionRepository questionRepository;
    ObjectMapper objectMapper = new ObjectMapper();
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
        questionTypes.add(new LabelValue("q2", "OneAnswerQuestion"));
        questionTypes.add(new LabelValue("q3", "NEW"));

        model.addAttribute("questionTypes", questionTypes);
        return "create-test";
    }

    @PostMapping("/upload_test")
    public String uploadTest(@RequestParam("testName") String testName){
        return "create-test";
    }

    @RequestMapping(value = "/getType", method = RequestMethod.POST)
    public String getType(HttpEntity<String> httpEntity) throws JSONException {
        String type = new JSONObject(httpEntity.getBody()).getString("type");

        return "fragments/q1";
    }

    @PostMapping(value = "/sendTest")
    public String sendTest(HttpEntity<String> httpEntity) throws JsonProcessingException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

        RawTest test = objectMapper.readValue(httpEntity.getBody(), RawTest.class);
        Set<Class> classes = classAccessorService.findAllClassesUsingClassLoader(
                "com.tests.testsapp.entities.Questions");
        for (RawQuestion question: test.getQuestions()
             ) {
            for (Class cl: classes
                 ) {
                if(cl.getName().contains(question.getType())){
                    Question question_class = (Question) cl.getConstructor(String.class).newInstance("");
                    question_class.setQuestionText(question.getqContent());
                    question_class.serialize(question.getAnswers());
                    questionRepository.save(question_class);
                }
            }
        }
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
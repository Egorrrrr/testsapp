package com.tests.testsapp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tests.testsapp.entities.Question;
import com.tests.testsapp.entities.Test;
import com.tests.testsapp.entities.json.RawQuestion;
import com.tests.testsapp.entities.json.RawTest;
import com.tests.testsapp.repos.QuestionRepository;
import com.tests.testsapp.repos.TestRepository;
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
import java.util.HashSet;
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
    @Autowired
    public TestRepository testRepository;
    ObjectMapper objectMapper = new ObjectMapper();
    @GetMapping("/home")
    public String home(Model model){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((AppUserDetails)principal).getUsername();
        String role = ((AppUserDetails)principal).getAuthorities().toArray()[0].toString();
        List<Test> testList = testRepository.findAll();
        model.addAttribute("name", username);
        model.addAttribute("role", role);
        model.addAttribute("tests", testList);
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

    @GetMapping("/test")
    public String getTest(@RequestParam String id, Model model){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((AppUserDetails)principal).getUsername();
        List<Question> questions = questionRepository.findQuestionsByTestId(Long.parseLong(id));

        model.addAttribute("username", username);
        model.addAttribute("tests", questions);
        return "test";
    }

    @GetMapping("/create-test")
    public String createTest(Model model) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String role = ((AppUserDetails)principal).getAuthorities().toArray()[0].toString();
        if(!role.equals("admin") ){
            return "403";
        }
        Set<Class> classes = classAccessorService.findAllClassesUsingClassLoader(
                "com.tests.testsapp.entities.Questions");
        List<LabelValue> questionTypes = new ArrayList<>();
        for (Class cl: classes
             ) {
            Question question_class = (Question) cl.getConstructor(String.class).newInstance("");
            questionTypes.add(new LabelValue(question_class.getConstructorPath(), question_class.getQuestionName()));
        }
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
        Test cookedTest = new Test();
        cookedTest.setName(test.getName());
        cookedTest = testRepository.save(cookedTest);
        for (RawQuestion question: test.getQuestions()
             ) {
            for (Class cl: classes
                 ) {
                Question question_class = (Question) cl.getConstructor(String.class).newInstance("");
                if(question_class.getQuestionName().equals(question.getType())){
                    question_class.setQuestionText(question.getqContent());
                    question_class.serialize(question.getAnswers());
                    question_class.setTestId(cookedTest.getId());
                    question_class = questionRepository.save(question_class);

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
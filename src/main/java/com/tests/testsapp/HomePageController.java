package com.tests.testsapp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tests.testsapp.entities.Question;
import com.tests.testsapp.entities.Statistic;
import com.tests.testsapp.entities.Test;
import com.tests.testsapp.entities.User;
import com.tests.testsapp.entities.json.RawAnswers;
import com.tests.testsapp.entities.json.RawQuestion;
import com.tests.testsapp.entities.json.RawTest;
import com.tests.testsapp.joor.Reflect;
import com.tests.testsapp.repos.QuestionRepository;
import com.tests.testsapp.repos.StatisticRepository;
import com.tests.testsapp.repos.TestRepository;
import com.tests.testsapp.repos.UserRepository;
import com.tests.testsapp.services.AppUserDetailService;
import com.tests.testsapp.services.AppUserDetails;
import com.tests.testsapp.services.ClassAccessorService;
import net.bytebuddy.dynamic.scaffold.MethodGraph;

import org.json.JSONException;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.awt.desktop.QuitEvent;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

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
    @Autowired
    public UserRepository userRepository;
    @Autowired
    public StatisticRepository statisticRepository;
    ObjectMapper objectMapper = new ObjectMapper();
    Set<Class> classes =new HashSet<>();
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
    public String createTest(Model model) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, IOException, ClassNotFoundException {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String role = ((AppUserDetails)principal).getAuthorities().toArray()[0].toString();
        if(!role.equals("admin") ){
            return "";

        }
        List<LabelValue> questionTypes = new ArrayList<>();
        for (Class cl: classAccessorService.getAllClasses("D:/testsapp/classes")
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
    public String getType(HttpEntity<String> httpEntity) throws JSONException, IOException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        String type = new JSONObject(httpEntity.getBody()).getString("type");
        for (Class cl: classAccessorService.getAllClasses("D:/testsapp/classes")
             ) {
            Question question_class = (Question) cl.getConstructor(String.class).newInstance("");
            if(question_class.getQuestionName().equals(type)){
                return question_class.getConstructorPath();
            }
        }
        return "";
    }

    @PostMapping(value = "/sendTest")
    public String sendTest(HttpEntity<String> httpEntity) throws IOException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

        RawTest test = objectMapper.readValue(httpEntity.getBody(), RawTest.class);
        List<Class> classes = classAccessorService.getAllClasses("D:/testsapp/classes");
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

        return "redirect:home";
    }
    @PostMapping("/end-test")
    public String endTest(HttpEntity<String> httpEntity) throws JsonProcessingException {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user =  userRepository.findByUsername(((AppUserDetails)principal).getUsername());
        RawAnswers test = objectMapper.readValue(httpEntity.getBody(), RawAnswers.class);
        List<Question> questions = questionRepository.findQuestionsByTestId((long)test.getId());
        int correctCount = 0;
        for (int i = 0; i<questions.size(); i++){
            Question question = questions.get(i);
            if(question.check(test.getAnswers().get(i))) {
                correctCount++;
            }

        }
        Statistic statistic = new Statistic();
        statistic.setUserId(user.getId());
        statistic.setTestId((long)test.getId());
        statistic.setSolvedCorrect(correctCount);
        statistic.setTotalQuestions(questions.size());
        statistic.setTimestamp(new Date());
        statisticRepository.save(statistic);
        user.getStats().add(statistic);
        Test testToWrite = testRepository.findTestById((long)test.getId());
        testToWrite.getStats().add(statistic);
        testRepository.save(testToWrite);
        userRepository.save(user);
        return "create-test";
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
package com.tests.testsapp;

import com.tests.testsapp.entities.OneAnswerQuestion;
import com.tests.testsapp.entities.Question;
import com.tests.testsapp.entities.Test;
import com.tests.testsapp.repos.QuestionRepository;
import com.tests.testsapp.repos.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TestsappApplication {


	public static void main(String[] args) {

		SpringApplication.run(TestsappApplication.class, args);
	}

}

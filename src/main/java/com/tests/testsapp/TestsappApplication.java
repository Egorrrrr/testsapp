package com.tests.testsapp;


import com.tests.testsapp.joor.Reflect;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Path;
import com.tests.testsapp.entities.Question;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import javax.persistence.Entity;

import javax.persistence.Transient;


@SpringBootApplication
public class TestsappApplication {


	public static void main(String[] args) throws IOException, URISyntaxException, InvocationTargetException, IllegalAccessException, NoSuchMethodException, ClassNotFoundException, InstantiationException {

		SpringApplication.run(TestsappApplication.class, args);
	}

}

package com.tests.testsapp.services;

import com.tests.testsapp.entities.Question;
import com.tests.testsapp.joor.Reflect;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ClassAccessorService {

    public Set<String> classes = new HashSet<>();
    public List<Class> listClasses = new ArrayList<>();
    public List<Class> getAllClasses(String path) throws IOException {
        File folder = new File(path);
        for (final File fileEntry : folder.listFiles()) {
                System.out.println(fileEntry.getName());
                if(classes.contains(fileEntry.getAbsolutePath())){
                    continue;
                }
                else {
                    classes.add(fileEntry.getAbsolutePath());
                }
                String java = Files.readString(Path.of(fileEntry.getAbsolutePath()));
                String name = fileEntry.getName().replaceAll(".java", "");
                Class aClass = Reflect.compile("com.tests.testsapp.entities.questions."+name, java, Question.class.getClassLoader()).get();
                listClasses.add(aClass);

        }
        return listClasses;
    }

}

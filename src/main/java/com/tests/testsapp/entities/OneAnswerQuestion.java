package com.tests.testsapp.entities;


import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

import javax.persistence.Entity;
import javax.persistence.PersistenceUnit;
import javax.persistence.Transient;
import java.util.List;

@Entity
public class OneAnswerQuestion extends Question {
    @Transient
    public final String QUESTION_NAME = "Вопрос с одним выбором";
    @Transient
    public final String TEMPLATE_PATH = "path";
    @Transient
    public final String CONSTRUCTOR_PATH = "path";
    @Transient
    private int correctOption;

    @Override
    public boolean check(String answer){

        return false;
    }

    @Override
    public void setPath(){
        setTemplatePath(TEMPLATE_PATH);
    }

    @Override
    public void setConst() {
        setConstructorPath(CONSTRUCTOR_PATH);
    }
}

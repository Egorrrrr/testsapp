package com.tests.testsapp.entities.Questions;


import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.tests.testsapp.entities.Question;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import javax.persistence.Entity;
import javax.persistence.PersistenceUnit;
import javax.persistence.Transient;
import javax.print.Doc;
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

    public OneAnswerQuestion() {
        super();
    }
    public OneAnswerQuestion(String a) {
        super();
    }
    @Override
    public boolean check(String answer){

        return false;
    }

    @Override
    public boolean serialize(String html){
        Document answersHtml = Jsoup.parse(html);
        String answer = answersHtml.getElementsByTag("input").get(0).val();
        this.setAnswerContent(answer);
        return true;
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

package com.tests.testsapp.entities.questions;


import com.tests.testsapp.entities.Question;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import javax.persistence.Entity;
import javax.persistence.Transient;


@Entity
public class OneSelectAnswerQuestion extends Question {
    @Transient
    public final String QUESTION_NAME = "Вопрос с одним выбором";

    @Transient
    public final String TEMPLATE_PATH = "fragments/one-answer-selection";

    @Transient
    public final String CONSTRUCTOR_PATH = "fragments/one-answer-selection-constructor";


    @Transient
    private int correctOption;

    public OneSelectAnswerQuestion() {
        super();
        setTemplatePath(TEMPLATE_PATH);
        setConstructorPath(CONSTRUCTOR_PATH);
        setQuestionName(QUESTION_NAME);
    }

    public OneSelectAnswerQuestion(String a) {
        super();
        setTemplatePath(TEMPLATE_PATH);
        setConstructorPath(CONSTRUCTOR_PATH);
        setQuestionName(QUESTION_NAME);


    }
    @Override
    public boolean check(String answer){
        Document answersHtml = Jsoup.parse(answer);
        String user_answer = answersHtml.getElementsByTag("input").get(0).val();
        return user_answer.equals(this.getAnswerContent());
    }

    @Override
    public boolean serialize(String html){
        Document answersHtml = Jsoup.parse(html);
        String answer = answersHtml.getElementsByTag("input").get(0).val();
        this.setAnswerContent(answer);
        return true;
    }


}

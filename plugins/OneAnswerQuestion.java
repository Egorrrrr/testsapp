package com.tests.testsapp.entities.questions;



import com.tests.testsapp.entities.Question;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import javax.persistence.Entity;

import javax.persistence.Transient;


@Entity
public class OneAnswerQuestion extends Question {
    @Transient
    public final String QUESTION_NAME = "Вопрос с одним словом";

    @Transient
    public final String TEMPLATE_PATH = "fragments/one-answer";

    @Transient
    public final String CONSTRUCTOR_PATH = "fragments/one-answer";


    @Transient
    private int correctOption;

    public OneAnswerQuestion() {
        super();
        setTemplatePath(TEMPLATE_PATH);
        setConstructorPath(CONSTRUCTOR_PATH);
        setQuestionName(QUESTION_NAME);
    }

    public OneAnswerQuestion(String a) {
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

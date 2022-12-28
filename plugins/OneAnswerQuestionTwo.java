package com.tests.testsapp.entities.questions;



import com.tests.testsapp.entities.Question;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import javax.persistence.Entity;

import javax.persistence.Transient;


@Entity
public class OneAnswerQuestionTwo extends Question {
    @Transient
    public final String QUESTION_NAME = "Вопрос с множественным ответом";

    @Transient
    public final String TEMPLATE_PATH = "fragments/one-answer-selection";

    @Transient
    public final String CONSTRUCTOR_PATH = "fragments/one-answer-selection-constructor";


    @Transient
    private int correctOption;

    public OneAnswerQuestionTwo() {
        super();
        setTemplatePath(TEMPLATE_PATH);
        setConstructorPath(CONSTRUCTOR_PATH);
        setQuestionName(QUESTION_NAME);
    }

    public OneAnswerQuestionTwo(String a) {
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
    public boolean serialize(String html) {
        try {
            Document answersHtml = Jsoup.parse(html);
            JSONObject obj = new JSONObject();
            obj.put("answers", new JSONArray());
            obj.put("questions", new JSONArray());
            int i = 0;
            for (Element el : answersHtml.getElementsByTag("input")
            ) {
                if (el.toString().contains("this.setAttribute")) {
                    if (el.attributes().get("checked").contains("true")) {
                        obj.getJSONArray("answers").put(i);
                        obj.getJSONArray("questions").put(el.val());
                    }

                }
                i++;
            }
            this.setAnswerContent(obj.toString());
            return true;
        } catch (Exception e) {
            return false;
        }

    }


}

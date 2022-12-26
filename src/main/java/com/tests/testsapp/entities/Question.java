package com.tests.testsapp.entities;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Question {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String questionText;
    @Transient
    private String templatePath;
    @Transient
    private String constructorPath;
    private String answerContent;


    @ManyToMany
    Set<Question> tests;
    public Question(String filler){}
    public Question() {}
    public void setPath(){}
    public void setConst(){}
    public boolean check(String answer){return false;}

    public boolean serialize(String question) {return false;}

    @Transient
    public String getConstructorPath() {
        return constructorPath;
    }
    @Transient
    public void setConstructorPath(String constructorPath) {
        this.constructorPath = constructorPath;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Transient
    public String getTemplatePath() {
        return templatePath;
    }

    @Transient
    public void setTemplatePath(String templatePath) {
        this.templatePath = templatePath;
    }

    public String getAnswerContent() {
        return answerContent;
    }

    public void setAnswerContent(String answerContent) {
        this.answerContent = answerContent;
    }


}

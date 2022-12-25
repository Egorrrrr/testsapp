package com.tests.testsapp.entities;


import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
public class Test {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String Name;
    @ManyToMany
    Set<Question> linkedQuestions;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Set<Question> getLinkedQuestions() {
        return linkedQuestions;
    }

    public void setLinkedQuestions(Set<Question> linkedQuestions) {
        this.linkedQuestions = linkedQuestions;
    }
}

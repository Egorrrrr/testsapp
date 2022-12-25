package com.tests.testsapp.entities;


import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
public class Test {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @ManyToMany
    Set<Question> likedCourses;
}

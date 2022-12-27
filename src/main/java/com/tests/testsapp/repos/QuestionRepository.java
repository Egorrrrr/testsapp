package com.tests.testsapp.repos;

import com.tests.testsapp.entities.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    Question findQuestionById(Long id);
    List<Question> findQuestionsByTestId(Long id);

}

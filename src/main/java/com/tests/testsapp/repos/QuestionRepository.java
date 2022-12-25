package com.tests.testsapp.repos;

import com.tests.testsapp.entities.Question;
import com.tests.testsapp.entities.Test;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    Test findQuestionById(Long id);
}

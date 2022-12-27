package com.tests.testsapp.repos;

import com.tests.testsapp.entities.Question;
import com.tests.testsapp.entities.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestRepository extends JpaRepository<Test, Long> {
    Test findTestById(Long id);

}

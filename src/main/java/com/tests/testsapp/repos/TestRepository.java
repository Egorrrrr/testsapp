package com.tests.testsapp.repos;

import com.tests.testsapp.entities.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestRepository extends JpaRepository<Test, Long> {
    Test findTestById(Long id);
}

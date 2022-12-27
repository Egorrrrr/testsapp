package com.tests.testsapp.repos;

import com.tests.testsapp.entities.Statistic;
import com.tests.testsapp.entities.Test;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatisticRepository extends JpaRepository<Statistic, Long> {
    Test findStatisticById(Long id);
}

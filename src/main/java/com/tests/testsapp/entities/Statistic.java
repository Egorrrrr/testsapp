package com.tests.testsapp.entities;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Statistic {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private Long testId;
    private Long userId;
    @Basic(optional = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;
    private Integer solvedCorrect;
    private Integer totalQuestions;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTestId() {
        return testId;
    }

    public void setTestId(Long testId) {
        this.testId = testId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getSolvedCorrect() {
        return solvedCorrect;
    }

    public void setSolvedCorrect(Integer solvedCorrect) {
        this.solvedCorrect = solvedCorrect;
    }

    public Integer getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(Integer totalQuestions) {
        this.totalQuestions = totalQuestions;
    }
}

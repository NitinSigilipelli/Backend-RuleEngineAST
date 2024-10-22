package com.example.rule_engine_with_ast.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "rule_evaluation_logs")
public class EvaluateRuleLog {
    @Id
    private String id; // Automatically generated ID

    private String ruleString; // The rule string
    private String[] userData; // User data used for evaluation
    private boolean response; // The evaluation result
    private Date timestamp; // Timestamp for when the evaluation was made

    // Getters and Setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRuleString() {
        return ruleString;
    }

    public void setRuleString(String ruleString) {
        this.ruleString = ruleString;
    }

    public String[] getUserData() {
        return userData;
    }

    public void setUserData(String[] userData) {
        this.userData = userData;
    }

    public boolean isResponse() {
        return response;
    }

    public void setResponse(boolean response) {
        this.response = response;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}

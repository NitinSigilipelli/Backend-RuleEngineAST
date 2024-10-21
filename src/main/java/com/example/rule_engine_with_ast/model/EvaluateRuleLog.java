package com.example.rule_engine_with_ast.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "evaluate_rules")
public class EvaluateRuleLog {
    @Id
    private String id;
    private String ruleString;
    private String[] userData;
    private boolean response;

    // Constructors, getters, and setters
    public EvaluateRuleLog() {}

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
}

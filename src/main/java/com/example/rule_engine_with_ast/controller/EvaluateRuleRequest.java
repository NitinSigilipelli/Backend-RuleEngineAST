package com.example.rule_engine_with_ast.controller;

public class EvaluateRuleRequest {
    private String ruleString;
    private String[] userData;

    // Getters and setters
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
}

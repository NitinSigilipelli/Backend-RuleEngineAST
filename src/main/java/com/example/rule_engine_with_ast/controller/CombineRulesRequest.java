package com.example.rule_engine_with_ast.controller;

public class CombineRulesRequest {
    private String[] ruleStrings;
    private String operator;

    // Getters and setters...
    public String[] getRuleStrings() {
        return ruleStrings;
    }

    public void setRuleStrings(String[] ruleStrings) {
        this.ruleStrings = ruleStrings;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }
}


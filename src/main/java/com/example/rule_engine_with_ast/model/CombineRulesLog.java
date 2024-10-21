package com.example.rule_engine_with_ast.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "combine_rules")
public class CombineRulesLog {
    @Id
    private String id;
    private String[] ruleStrings;
    private String operator;
    private Node response;

    // Constructors, getters, and setters
    public CombineRulesLog() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public Node getResponse() {
        return response;
    }

    public void setResponse(Node response) {
        this.response = response;
    }
}

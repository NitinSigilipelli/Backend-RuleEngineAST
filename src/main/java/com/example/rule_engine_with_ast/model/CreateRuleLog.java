package com.example.rule_engine_with_ast.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "create_rules")
public class CreateRuleLog {
    @Id
    private String id;
    private String ruleString;
    private Node response;
    private Date timestamp;

    // Constructors, getters, and setters
    public CreateRuleLog() {}

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

    public Node getResponse() {
        return response;
    }

    public void setResponse(Node response) {
        this.response = response;
    }
    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}

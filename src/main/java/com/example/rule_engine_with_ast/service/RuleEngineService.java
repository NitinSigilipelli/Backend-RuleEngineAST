package com.example.rule_engine_with_ast.service;

import com.example.rule_engine_with_ast.model.Node;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class RuleEngineService {

    // Create AST from rule string
    public Node createRule(String ruleString) {
        // Parsing the rule string into an AST
        return parseRuleString(ruleString);
    }

    // Combine multiple rules into a single AST using a specified operator (AND/OR)
    public Node combineRules(String operator, Node... rules) {
        if (rules.length == 0) return null;
        Node combined = rules[0];
        for (int i = 1; i < rules.length; i++) {
            combined = new Node("operator", operator, combined, rules[i]);
        }
        return combined;
    }

    // Evaluate rule against user data
    public boolean evaluateRule(Node rule, String[] userData) {
        // Assuming userData format: [age, department, salary, experience]
        if (rule == null || userData == null || userData.length < 4) {
            throw new IllegalArgumentException("Invalid rule or user data");
        }

        Map<String, Object> context = new HashMap<>();
        context.put("age", Integer.parseInt(userData[0]));
        context.put("department", userData[1]);
        context.put("salary", Double.parseDouble(userData[2]));
        context.put("experience", Integer.parseInt(userData[3]));

        return evaluateNode(rule, context);
    }

    // Recursively evaluate the AST node
    private boolean evaluateNode(Node node, Map<String, Object> context) {
        if (node == null) return false;

        switch (node.getType()) {
            case "operand":
                return evaluateOperand(node, context);
            case "operator":
                return evaluateOperator(node, context);
            default:
                throw new IllegalArgumentException("Unknown node type: " + node.getType());
        }
    }

    // Evaluate an operand node
    private boolean evaluateOperand(Node node, Map<String, Object> context) {
        String operandValue = node.getValue();
        String[] parts = operandValue.split(" ");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Invalid operand format: " + operandValue);
        }

        String variable = parts[0];
        String operator = parts[1];
        String value = parts[2];

        Object contextValue = context.get(variable);
        if (contextValue == null) {
            throw new IllegalArgumentException("Variable not found in context: " + variable);
        }

        // Perform comparison based on the operator
        switch (operator) {
            case ">":
                return compareNumeric((Number) contextValue, Double.parseDouble(value));
            case "<":
                return compareNumeric((Number) contextValue, -Double.parseDouble(value));
            case "=":
                return compareEquality(contextValue.toString(), value);
            case ">=":
                return compareNumeric((Number) contextValue, Double.parseDouble(value), true);
            case "<=":
                return compareNumeric((Number) contextValue, -Double.parseDouble(value), true);
            default:
                throw new IllegalArgumentException("Unknown operator: " + operator);
        }
    }

    // Compare numeric values
    private boolean compareNumeric(Number contextValue, double comparisonValue) {
        return contextValue.doubleValue() > comparisonValue;
    }

    // Overloaded method for inclusive comparisons
    private boolean compareNumeric(Number contextValue, double comparisonValue, boolean inclusive) {
        return inclusive ? contextValue.doubleValue() >= comparisonValue : contextValue.doubleValue() > comparisonValue;
    }

    // Compare equality for strings
    private boolean compareEquality(String contextValue, String value) {
        // Remove quotes if present in value
        value = value.startsWith("'") && value.endsWith("'") ? value.substring(1, value.length() - 1) : value;
        return contextValue.equals(value);
    }

    // Evaluate operator node (AND/OR)
    private boolean evaluateOperator(Node node, Map<String, Object> context) {
        boolean leftResult = evaluateNode(node.getLeft(), context);
        boolean rightResult = evaluateNode(node.getRight(), context);

        switch (node.getValue()) {
            case "AND":
                return leftResult && rightResult;
            case "OR":
                return leftResult || rightResult;
            default:
                throw new IllegalArgumentException("Unknown operator: " + node.getValue());
        }
    }

    // Simple parsing method to generate AST from rule string
    private Node parseRuleString(String ruleString) {
        if (ruleString.contains("AND")) {
            String[] parts = ruleString.split("AND", 2);
            Node left = parseRuleString(parts[0].trim());
            Node right = parseRuleString(parts[1].trim());
            return new Node("operator", "AND", left, right);
        } else if (ruleString.contains("OR")) {
            String[] parts = ruleString.split("OR", 2);
            Node left = parseRuleString(parts[0].trim());
            Node right = parseRuleString(parts[1].trim());
            return new Node("operator", "OR", left, right);
        } else {
            return new Node("operand", ruleString.trim(), null, null);
        }
    }
}

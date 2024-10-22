package com.example.rule_engine_with_ast.model;

import java.util.ArrayList;

public class Node {
    private String type; // "operator" or "operand"
    private String value; // e.g., "age > 30", "AND", "OR"
    private Node left; // left child (for operators)
    private Node right; // right child (for operators)
    private Object operandValue; // Actual value for operands (e.g., 30 or "Sales")

    // Constructor for operator nodes (e.g., AND, OR)
    public Node(String type, String value, Node left, Node right) {
        this.type = type;
        this.value = value;
        this.left = left;
        this.right = right;
    }

    // Constructor for operand nodes (e.g., age > 30)
    public Node(String type, String value, Object operandValue) {
        this.type = type;
        this.value = value;
        this.operandValue = operandValue;
    }

    // Getters and setters...
    public String getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    public Node getLeft() {
        return left;
    }

    public Node getRight() {
        return right;
    }

    public Object getOperandValue() {
        return operandValue;
    }
}

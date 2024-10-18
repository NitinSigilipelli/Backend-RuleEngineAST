package com.example.rule_engine_with_ast.model;

public class Node {
    private String type; // "operator" or "operand"
    private String value; // e.g., "age > 30"
    private Node left; // left child
    private Node right; // right child

    public Node(String type, String value, Node left, Node right) {
        this.type = type;
        this.value = value;
        this.left = left;
        this.right = right;
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
}

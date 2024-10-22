package com.example.rule_engine_with_ast.model;

// Enum for comparison operators
public enum Comparison {
    GREATER(">"), LESS("<"), EQUAL("="), GREATER_EQUAL(">="), LESS_EQUAL("<=");

    private final String symbol;
    Comparison(String symbol) { this.symbol = symbol; }
    public String getSymbol() { return symbol; }

    public static Comparison fromString(String s) {
        for (Comparison c : Comparison.values()) {
            if (c.getSymbol().equals(s)) return c;
        }
        throw new IllegalArgumentException("Invalid comparison: " + s);
    }
}

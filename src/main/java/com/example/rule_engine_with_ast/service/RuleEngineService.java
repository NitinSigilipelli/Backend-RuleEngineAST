package com.example.rule_engine_with_ast.service;

import com.example.rule_engine_with_ast.controller.CombineRulesRequest;
import com.example.rule_engine_with_ast.model.*;
import com.example.rule_engine_with_ast.repository.CombineRuleRepository;
import com.example.rule_engine_with_ast.repository.CreateRuleRepository;
import com.example.rule_engine_with_ast.repository.EvaluateRuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class RuleEngineService {
    private final CreateRuleRepository createRuleRepository;
    private final CombineRuleRepository combineRuleRepository;
    private final EvaluateRuleRepository evaluateRuleRepository;
    // Create AST from rule string
    @Autowired
    public RuleEngineService(CreateRuleRepository createRuleRepository,
                             CombineRuleRepository combineRuleRepository,
                             EvaluateRuleRepository evaluateRuleRepository) {
        this.createRuleRepository = createRuleRepository;
        this.combineRuleRepository = combineRuleRepository;
        this.evaluateRuleRepository = evaluateRuleRepository;
    }
    public static Node createRule(String ruleString) {
        List<String> tokens = tokenize(ruleString);
        return parseExpression(tokens.listIterator());
    }

    // Tokenize the rule string into meaningful components (AND, OR, >, <, etc.)
    private static List<String> tokenize(String rule) {
        List<String> tokens = new ArrayList<>();
        Matcher matcher = Pattern.compile("\\(|\\)|AND|OR|>=|<=|>|<|=|\\w+|'[^']*'|\\d+").matcher(rule);

        while (matcher.find()) {
            tokens.add(matcher.group());
        }
        return tokens;
    }

    // Parse expressions, handling "OR" with the lowest precedence
    private static Node parseExpression(ListIterator<String> tokens) {
        Node left = parseTerm(tokens);

        while (tokens.hasNext()) {
            String token = tokens.next();
            if (token.equals("OR")) {
                Node right = parseTerm(tokens);
                left = new Node("operator", "OR", left, right);
            } else {
                // Rewind the token if it's not "OR"
                tokens.previous();
                break;
            }
        }
        return left;
    }

    // Parse terms, handling "AND" with medium precedence
    private static Node parseTerm(ListIterator<String> tokens) {
        Node left = parseFactor(tokens);

        while (tokens.hasNext()) {
            String token = tokens.next();
            if (token.equals("AND")) {
                Node right = parseFactor(tokens);
                left = new Node("operator", "AND", left, right);
            } else {
                // Rewind the token if it's not "AND"
                tokens.previous();
                break;
            }
        }
        return left;
    }

    // Parse factors (operands or nested expressions)
    private static Node parseFactor(ListIterator<String> tokens) {
        String token = tokens.next();

        if (token.equals("(")) {
            // Handle nested expression inside parentheses
            Node expression = parseExpression(tokens);
            tokens.next(); // Consume closing parenthesis ")"
            return expression;
        } else {
            // Handle operand (e.g., age > 30)
            String attribute = token;            // e.g., "age"
            String comparison = tokens.next();   // e.g., ">"
            String valueToken = tokens.next();   // e.g., "30"
            Object value = parseValue(valueToken);

            // Create operand node with attribute, comparison, and value
            return new Node("operand", attribute + " " + comparison + " " + value, null, null);
        }
    }

    // Convert value token into the appropriate type (integer or string)
    private static Object parseValue(String token) {
        if (token.matches("\\d+")) {
            return Integer.parseInt(token); // Numeric value
        } else if (token.startsWith("'") && token.endsWith("'")) {
            return token.substring(1, token.length() - 1); // String value (without quotes)
        }
        throw new IllegalArgumentException("Invalid value: " + token);
    }

    public Node combineRulesWithOperator(String[] ruleStrings, String operator) {
        if (ruleStrings == null || ruleStrings.length == 0) {
            throw new IllegalArgumentException("Rule list cannot be null or empty.");
        }

        List<Node> asts = new ArrayList<>();
        Map<String, Integer> operatorFrequency = new HashMap<>();

        for (String rule : ruleStrings) {
            Node ast = createRule(rule);
            asts.add(ast);

            // If no operator is provided, calculate frequency for heuristic selection
            if (operator == null || operator.isEmpty()) {
                countOperators(rule, operatorFrequency);
            }
        }

        // Determine the operator if it's not provided
        String combineOperator;
        if (operator == null || operator.isEmpty()) {
            combineOperator = operatorFrequency.getOrDefault("AND", 0) >= operatorFrequency.getOrDefault("OR", 0) ? "AND" : "OR";
        } else {
            combineOperator = operator.toUpperCase(); // Use the user-provided operator
        }

        // Combine the ASTs using the selected operator
        Node combinedAST = asts.get(0);
        for (int i = 1; i < asts.size(); i++) {
            combinedAST = new Node("operator", combineOperator, combinedAST, asts.get(i));
        }

        return combinedAST;
    }

    // Helper function to count operators in the rule string (as in the earlier example)
    private static void countOperators(String rule, Map<String, Integer> operatorFrequency) {
        Matcher matcher = Pattern.compile("AND|OR").matcher(rule);
        while (matcher.find()) {
            String operator = matcher.group();
            operatorFrequency.put(operator, operatorFrequency.getOrDefault(operator, 0) + 1);
        }
    }

    // Evaluate rule against user data
    public boolean evaluateRule(Node rule, String[] userData) {

        Map<String, Object> context = new HashMap<>();
        context.put("age", Integer.parseInt(userData[0]));         // age as an integer
        context.put("department", userData[1]);                    // department as a string
        context.put("salary", Double.parseDouble(userData[2]));    // salary as a double
        context.put("experience", Integer.parseInt(userData[3]));
        boolean result = evaluateNode(rule, context);

        EvaluateRuleLog evaluateRuleLog = new EvaluateRuleLog();
        evaluateRuleLog.setRuleString(rule.toString());  // Store the string representation of the rule
        evaluateRuleLog.setUserData(userData);
        evaluateRuleLog.setResponse(result);
        evaluateRuleRepository.save(evaluateRuleLog);

        return result;
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
}

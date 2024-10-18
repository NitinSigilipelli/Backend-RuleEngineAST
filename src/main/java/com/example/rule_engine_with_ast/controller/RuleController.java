package com.example.rule_engine_with_ast.controller;

import com.example.rule_engine_with_ast.model.Node;
import com.example.rule_engine_with_ast.service.RuleEngineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rules")
public class RuleController {

    private final RuleEngineService ruleEngine;

    @Autowired
    public RuleController(RuleEngineService ruleEngine) {
        this.ruleEngine = ruleEngine;
    }

    // Create a new rule from a rule string
    @PostMapping("/create")
    public Node createRule(@RequestBody RuleRequest ruleRequest) {
        return ruleEngine.createRule(ruleRequest.getRuleString());
    }

    // Combine multiple rules using a logical operator (AND/OR)
    @PostMapping("/combine")
    public Node combineRules(@RequestBody CombineRulesRequest request) {
        Node[] ruleNodes = new Node[request.getRuleStrings().length];
        for (int i = 0; i < request.getRuleStrings().length; i++) {
            ruleNodes[i] = ruleEngine.createRule(request.getRuleStrings()[i]);
        }
        return ruleEngine.combineRules(request.getOperator(), ruleNodes);
    }

    // Evaluate the rule based on provided user data
    @PostMapping("/evaluate")
    public boolean evaluateRule(@RequestBody EvaluateRuleRequest request) {
        Node rule = ruleEngine.createRule(request.getRuleString());
        return ruleEngine.evaluateRule(rule, request.getUserData());
    }
}

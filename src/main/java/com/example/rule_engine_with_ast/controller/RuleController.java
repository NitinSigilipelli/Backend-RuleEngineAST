package com.example.rule_engine_with_ast.controller;

import com.example.rule_engine_with_ast.model.Node;
import com.example.rule_engine_with_ast.service.RuleEngineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
        return RuleEngineService.createRule(ruleRequest.getRuleString());
    }


    @PostMapping("/combine")
    public Node combineRules(@RequestBody CombineRulesRequest request) {
        String[] ruleStrings = request.getRuleStrings();
        String operator = request.getOperator();

        // Pass the rule strings and optional operator to the service
        return ruleEngine.combineRulesWithOperator(ruleStrings, operator);
    }
//     //Evaluate the rule based on provided user data
    @PostMapping("/evaluate")
    public boolean evaluateRule(@RequestBody EvaluateRuleRequest request) {
        Node rule = RuleEngineService.createRule(request.getRuleString());
        return ruleEngine.evaluateRule(rule, request.getUserData());
    }
}

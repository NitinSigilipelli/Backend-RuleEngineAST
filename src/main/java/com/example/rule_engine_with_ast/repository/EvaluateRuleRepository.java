package com.example.rule_engine_with_ast.repository;

import com.example.rule_engine_with_ast.model.EvaluateRuleLog;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EvaluateRuleRepository extends MongoRepository<EvaluateRuleLog, String> {
}

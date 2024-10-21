package com.example.rule_engine_with_ast.repository;

import com.example.rule_engine_with_ast.model.CombineRulesLog;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CombineRuleRepository extends MongoRepository<CombineRulesLog, String> {
}

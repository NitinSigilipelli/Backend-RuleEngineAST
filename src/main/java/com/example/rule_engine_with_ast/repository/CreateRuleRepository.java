package com.example.rule_engine_with_ast.repository;

import com.example.rule_engine_with_ast.model.CreateRuleLog;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CreateRuleRepository extends MongoRepository<CreateRuleLog, String> {
}

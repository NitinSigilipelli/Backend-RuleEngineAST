package com.example.rule_engine_with_ast;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

@SpringBootApplication(scanBasePackages = "com.example.rule_engine_with_ast", exclude = {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class , MongoAutoConfiguration.class})
public class RuleEngineWithAstApplication {
    public static void main(String[] args) {
        SpringApplication.run(RuleEngineWithAstApplication.class, args);
    }
}

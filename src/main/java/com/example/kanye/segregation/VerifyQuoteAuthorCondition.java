package com.example.kanye.segregation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

// TODO
public abstract class VerifyQuoteAuthorCondition implements Condition {
    private static final Logger LOGGER = LoggerFactory.getLogger(VerifyQuoteAuthorCondition.class);

//    @Override
//    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
//        // If the quote author is "KANYE", then we allow the bean creation
//
//    }
}

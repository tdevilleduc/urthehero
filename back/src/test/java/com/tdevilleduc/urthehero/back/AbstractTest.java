package com.tdevilleduc.urthehero.back;

import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractTest {

    @Autowired
    private CircuitBreakerRegistry registry;

    @BeforeEach
    void setUp() {
        resetAllCircuitBreakers();
    }

    @AfterEach
    void tearDown() {
        resetAllCircuitBreakers();
    }

    private void resetAllCircuitBreakers() {
        registry.getAllCircuitBreakers().forEach(circuitBreaker -> circuitBreaker.reset());
    }
}

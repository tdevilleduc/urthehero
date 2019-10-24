package com.tdevilleduc.urthehero.back;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        registry.getAllCircuitBreakers().forEach(CircuitBreaker::reset);
    }

    protected void checkHealthStatus(String circuitBreakerName, CircuitBreaker.State state) {
        CircuitBreaker circuitBreaker = registry.circuitBreaker(circuitBreakerName);
        assertEquals(state, circuitBreaker.getState());
    }
}

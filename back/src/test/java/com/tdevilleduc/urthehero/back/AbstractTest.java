package com.tdevilleduc.urthehero.back;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import org.junit.ClassRule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.MySQLContainer;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ContextConfiguration(initializers = {AbstractTest.Initializer.class})
public abstract class AbstractTest {

    @ClassRule
    public static final MySQLContainer mySqlContainer = new MySQLContainer()
            .withDatabaseName("test")
            .withUsername("test")
            .withPassword("test");

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

    static class Initializer
            implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            mySqlContainer.start();
            TestPropertyValues.of(
                    "spring.datasource.url=" + mySqlContainer.getJdbcUrl(),
                    "spring.datasource.username=" + mySqlContainer.getUsername(),
                    "spring.datasource.password=" + mySqlContainer.getPassword()
            ).applyTo(configurableApplicationContext.getEnvironment());
        }
    }
    
    private void resetAllCircuitBreakers() {
        registry.getAllCircuitBreakers().forEach(CircuitBreaker::reset);
    }

    protected void checkHealthStatus(String circuitBreakerName, CircuitBreaker.State state) {
        CircuitBreaker circuitBreaker = registry.circuitBreaker(circuitBreakerName);
        assertEquals(state, circuitBreaker.getState());
    }
}

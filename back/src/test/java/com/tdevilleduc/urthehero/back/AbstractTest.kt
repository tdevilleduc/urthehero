package com.tdevilleduc.urthehero.back

import io.github.resilience4j.circuitbreaker.CircuitBreaker
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry
import org.junit.ClassRule
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.test.context.ContextConfiguration
import org.testcontainers.containers.MySQLContainer
import java.util.function.Consumer

@ContextConfiguration(initializers = [AbstractTest.Initializer::class])
abstract class AbstractTest {
//    @Autowired
//    private lateinit var registry: CircuitBreakerRegistry
//
//    @BeforeEach
//    fun setUp() {
//        resetAllCircuitBreakers()
//    }
//
//    @AfterEach
//    fun tearDown() {
//        resetAllCircuitBreakers()
//    }

    internal class Initializer : ApplicationContextInitializer<ConfigurableApplicationContext> {
        override fun initialize(configurableApplicationContext: ConfigurableApplicationContext) {
            mySqlContainer.start()
            TestPropertyValues.of(
                    "spring.datasource.url=" + mySqlContainer.getJdbcUrl(),
                    "spring.datasource.username=" + mySqlContainer.getUsername(),
                    "spring.datasource.password=" + mySqlContainer.getPassword()
            ).applyTo(configurableApplicationContext.environment)
        }
    }

//    private fun resetAllCircuitBreakers() {
//        registry.allCircuitBreakers.forEach(Consumer { obj: CircuitBreaker -> obj.reset() })
//    }
//
//    protected fun checkHealthStatus(circuitBreakerName: String, state: CircuitBreaker.State) {
//        val circuitBreaker = registry.circuitBreaker(circuitBreakerName)
//        Assertions.assertEquals(state, circuitBreaker.state)
//    }

    companion object {
        internal class SpecifiedMySQLContainer(private val image: String) : MySQLContainer<SpecifiedMySQLContainer>(image)
        @ClassRule
        val mySqlContainer: MySQLContainer<*> = SpecifiedMySQLContainer("mysql:8.0.19")
                .withDatabaseName("test")
                .withUsername("test")
                .withPassword("test")
    }
}
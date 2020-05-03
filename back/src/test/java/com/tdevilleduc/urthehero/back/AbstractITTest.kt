package com.tdevilleduc.urthehero.back

import org.junit.ClassRule
import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.test.context.ContextConfiguration
import org.testcontainers.containers.PostgreSQLContainer

@ContextConfiguration(initializers = [AbstractITTest.Initializer::class])
abstract class AbstractITTest {
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
            postgreSQLContainer.start()
            TestPropertyValues.of(
                    "spring.datasource.url=" + postgreSQLContainer.getJdbcUrl(),
                    "spring.datasource.username=" + postgreSQLContainer.getUsername(),
                    "spring.datasource.password=" + postgreSQLContainer.getPassword()
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
        internal class SpecifiedPostgreSQLContainer : PostgreSQLContainer<SpecifiedPostgreSQLContainer>()
        @ClassRule
        val postgreSQLContainer: PostgreSQLContainer<*> = SpecifiedPostgreSQLContainer()
                .withDatabaseName("test")
                .withUsername("test")
                .withPassword("test")
    }
}
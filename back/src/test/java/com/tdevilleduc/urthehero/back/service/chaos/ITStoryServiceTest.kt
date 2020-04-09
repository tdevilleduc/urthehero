package com.tdevilleduc.urthehero.back.service.chaos

import com.tdevilleduc.urthehero.back.AbstractTest
import com.tdevilleduc.urthehero.back.BackApplication
import com.tdevilleduc.urthehero.back.exceptions.StoryNotFoundException
import com.tdevilleduc.urthehero.back.service.IStoryService
import org.hamcrest.Matchers
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.function.Executable
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

@ExtendWith(SpringExtension::class)
@SpringBootTest(classes = [BackApplication::class])
@ActiveProfiles("chaos-monkey")
class ITStoryServiceTest : AbstractTest() {
    val logger: Logger = LoggerFactory.getLogger(ITStoryServiceTest::class.java)

    @Autowired
    private lateinit var storyService: IStoryService

    @AfterEach
    @Throws(Exception::class)
    private fun tearDownOne() {
        disableChaosMonkey()
    }

    //    @Test
//    void findByPersonId_withResilience4jAndChaosMonkey() throws Exception {
//        Integer personId = 1;
//
//        enableChaosMonkey();
//
//        // check if circuitBreaker status is closed
//        checkHealthStatus(INSTANCE_STORY_SERVICE, CircuitBreaker.State.CLOSED);
//
//        // request 'minimumNumberOfCalls' times with error -> fallbackMethod
//        Stream.rangeClosed(1,5).forEach((count) -> {
//            List<Story> optional = storyService.findByPersonId(personId);
//            assertTrue(optional.isEmpty());
//        });
//
//        // check if circuitBreaker status is opened
//        checkHealthStatus(INSTANCE_STORY_SERVICE, CircuitBreaker.State.OPEN);
//
//        disableChaosMonkey();
//
//        // try to request again; circuitBreaker status should be opened -> fallbackMethod
//        Stream.rangeClosed(1,5).forEach((count) -> {
//            List<Story> optional = storyService.findByPersonId(personId);
//            assertTrue(optional.isEmpty());
//        });
//
//        // sleep during 'waitDurationInOpenState'
//        Thread.sleep(2000);
//
//        // check if circuitBreaker status is halfOpen
//        checkHealthStatus(INSTANCE_STORY_SERVICE, CircuitBreaker.State.HALF_OPEN);
//
//        // try to request again; circuitBreaker status should be closed -> ok
//        Stream.rangeClosed(1,3).forEach((count) -> {
//            List<Story> optional = storyService.findByPersonId(personId);
//            assertFalse(optional.isEmpty());
//            // TODO completer le test
//        });
//
//        // check if circuitBreaker status is closed
//        checkHealthStatus(INSTANCE_STORY_SERVICE, CircuitBreaker.State.CLOSED);
//    }

//    @Test
//    @Throws(Exception::class)
//    fun findByPageId_withResilience4jAndChaosMonkey() {
//        val storyId = 1
//        enableChaosMonkey()
//        checkHealthStatus(INSTANCE_STORY_SERVICE, CircuitBreaker.State.CLOSED)
//        Stream.rangeClosed(1, 5).forEach(Consumer {
//            Assertions.assertThrows(StoryNotFoundException::class.java, Executable { storyService.findById(storyId) })
//        })
//        checkHealthStatus(INSTANCE_STORY_SERVICE, CircuitBreaker.State.OPEN)
//        disableChaosMonkey()
//        Stream.rangeClosed(1, 5).forEach(Consumer {
//            Assertions.assertThrows(StoryNotFoundException::class.java, Executable { storyService.findById(storyId) })
//        })
//        TimeUnit.MINUTES.sleep(2)
//        checkHealthStatus(INSTANCE_STORY_SERVICE, CircuitBreaker.State.HALF_OPEN)
//        Stream.rangeClosed(1, 3).forEach(Consumer {
//            val story = storyService.findById(storyId)
//            Assertions.assertEquals(Integer.valueOf(1), story.storyId)
//            Assertions.assertEquals("Ulysse", story.title)
//            Assertions.assertEquals(Integer.valueOf(1), story.authorId)
//            Assertions.assertEquals(Integer.valueOf(1), story.firstPageId)
//            Assertions.assertEquals(java.lang.Long.valueOf(3), story.numberOfReaders)
//        })
//        checkHealthStatus(INSTANCE_STORY_SERVICE, CircuitBreaker.State.CLOSED)
//    }

    @Test
    fun delete_thenNotFound() {
        val storyId = 13
        Assertions.assertThrows(StoryNotFoundException::class.java, Executable { storyService.delete(storyId) })
    }

    private lateinit var mockMvc: MockMvc
    @Autowired
    private lateinit var webApplicationContext: WebApplicationContext

    @Throws(Exception::class)
    private fun enableChaosMonkey() {
        val mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build()
        mockMvc.perform(MockMvcRequestBuilders.post("/chaosmonkey/enable"))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.content().string(Matchers.`is`(Matchers.notNullValue())))
        if (logger.isInfoEnabled) {
            logger.info("chaosmonkey is enabled")
        }
    }

    @Throws(Exception::class)
    private fun disableChaosMonkey() {
        val mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build()
        mockMvc.perform(MockMvcRequestBuilders.post("/chaosmonkey/disable"))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.content().string(Matchers.`is`(Matchers.notNullValue())))
        if (logger.isInfoEnabled) {
            logger.info("chaosmonkey is disabled")
        }
    }
}
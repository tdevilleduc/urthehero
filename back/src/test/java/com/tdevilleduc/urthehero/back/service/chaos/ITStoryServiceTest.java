package com.tdevilleduc.urthehero.back.service.chaos;

import com.tdevilleduc.urthehero.back.AbstractTest;
import com.tdevilleduc.urthehero.back.BackApplication;
import com.tdevilleduc.urthehero.back.exceptions.StoryNotFoundException;
import com.tdevilleduc.urthehero.back.model.Story;
import com.tdevilleduc.urthehero.back.service.IStoryService;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.vavr.collection.Stream;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;

import static com.tdevilleduc.urthehero.back.config.ResilienceConstants.INSTANCE_STORY_SERVICE;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = BackApplication.class)
@ActiveProfiles("chaos-monkey")
public class ITStoryServiceTest extends AbstractTest {

    @Autowired
    private CircuitBreakerRegistry registry;
    @Autowired
    private IStoryService storyService;

    @AfterEach
    private void tearDown() throws Exception {
        disableChaosMonkey();
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


    @Test
    void findByPageId_withResilience4jAndChaosMonkey() throws Exception {
        Integer storyId = 1;

        enableChaosMonkey();

        checkHealthStatus(INSTANCE_STORY_SERVICE, CircuitBreaker.State.CLOSED);

        Stream.rangeClosed(1,5).forEach((count) -> {
            Optional<Story> optional = storyService.findById(storyId);
            assertFalse(optional.isPresent());
        });

        checkHealthStatus(INSTANCE_STORY_SERVICE, CircuitBreaker.State.OPEN);

        disableChaosMonkey();

        Stream.rangeClosed(1,5).forEach((count) -> {
            Optional<Story> optional = storyService.findById(storyId);
            assertFalse(optional.isPresent());
        });

        Thread.sleep(2000);

        checkHealthStatus(INSTANCE_STORY_SERVICE, CircuitBreaker.State.HALF_OPEN);

        Stream.rangeClosed(1,3).forEach((count) -> {
            Optional<Story> optional = storyService.findById(storyId);
            assertTrue(optional.isPresent());
            Story story = optional.get();
            assertEquals(Integer.valueOf(1), story.getStoryId());
            assertEquals("Ulysse", story.getTitle());
            assertEquals(Integer.valueOf(1), story.getAuthorId());
            assertEquals(Integer.valueOf(1), story.getFirstPageId());
            assertEquals(Long.valueOf(3), story.getNumberOfReaders());
        });

        checkHealthStatus(INSTANCE_STORY_SERVICE, CircuitBreaker.State.CLOSED);
    }

    @Test
    void delete_thenNotFound() {
        Integer storyId = 13;
        Assertions.assertThrows(StoryNotFoundException.class, () -> storyService.delete(storyId));
    }

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private void enableChaosMonkey() throws Exception {
        MockMvc mockMvc = webAppContextSetup(webApplicationContext).build();

        mockMvc.perform(post("/chaosmonkey/enable"))
                .andExpect(status().isOk())
                .andExpect(content().string(is(notNullValue())));
        if (log.isInfoEnabled()) {
            log.info("chaosmonkey is enabled");
        }
    }

    private void disableChaosMonkey() throws Exception {
        MockMvc mockMvc = webAppContextSetup(webApplicationContext).build();

        mockMvc.perform(post("/chaosmonkey/disable"))
                .andExpect(status().isOk())
                .andExpect(content().string(is(notNullValue())));

        if (log.isInfoEnabled()) {
            log.info("chaosmonkey is disabled");
        }

    }
}

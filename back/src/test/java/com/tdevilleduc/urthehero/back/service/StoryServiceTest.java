package com.tdevilleduc.urthehero.back.service;

import com.tdevilleduc.urthehero.back.AbstractTest;
import com.tdevilleduc.urthehero.back.BackApplication;
import com.tdevilleduc.urthehero.back.model.Story;
import com.tdevilleduc.urthehero.back.service.impl.StoryService;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreaker.State;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.vavr.collection.Stream;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = BackApplication.class)
public class StoryServiceTest extends AbstractTest {

    private final static String CIRCUIT_BREAKER_STORY_FIND_BY_PERSON_ID = "storyFindByPersonId";

    @Autowired
    private CircuitBreakerRegistry registry;
    @Autowired
    private StoryService storyService;

    @Test
    public void test_exists_thenCorrect() {
        Integer storyId = 1;
        boolean exists = storyService.exists(storyId);
        Assertions.assertTrue(exists);
    }

    @Test
    public void test_notExists_thenCorrect() {
        Integer storyId = 41;
        boolean notExists = storyService.notExists(storyId);
        Assertions.assertTrue(notExists);
    }

    @Test
    public void test_findByPageId_thenCorrect() {
        Integer storyId = 1;
        Optional<Story> optional = storyService.findById(storyId);

        Assertions.assertTrue(optional.isPresent());
        Story story = optional.get();
        Assertions.assertEquals(Integer.valueOf(1), story.getId());
        Assertions.assertEquals("Ulysse", story.getTitle());
        Assertions.assertEquals(Integer.valueOf(1), story.getAuthorId());
        Assertions.assertEquals(Integer.valueOf(1), story.getFirstPageId());
        Assertions.assertEquals(Long.valueOf(4), story.getNumberOfPages());
        Assertions.assertEquals(Long.valueOf(3), story.getNumberOfReaders());
        Assertions.assertEquals(4, story.getPages().size());
    }


    @Test
    public void findByIdWithIdNull() {
        assertEquals(Optional.empty(), storyService.findById(null));
    }

    @Test
    public void findByPersonIdWithResilience4j() throws InterruptedException {
        Integer personId = 1;

        // request 'minimumNumberOfCalls' times with error
        Stream.rangeClosed(1,5).forEach((count) -> assertThrows(IllegalArgumentException.class, () -> storyService.findByPersonId(null)));

        // check if circuitBreaker status is opened
        checkHealthStatus(CIRCUIT_BREAKER_STORY_FIND_BY_PERSON_ID, State.OPEN);

        // try to request again; circuitBreaker status should be opened -> RuntimeException
        Stream.rangeClosed(1,5).forEach((count) -> assertThrows(RuntimeException.class, () -> storyService.findByPersonId(personId)));

        // sleep during 'waitDurationInOpenState'
        Thread.sleep(2000);

        // try to request again; circuitBreaker status should be closed -> ok
        Stream.rangeClosed(1,3).forEach((count) -> storyService.findByPersonId(personId));

        // check if circuitBreaker status is closed
        checkHealthStatus(CIRCUIT_BREAKER_STORY_FIND_BY_PERSON_ID, State.CLOSED);
    }

    private void checkHealthStatus(String circuitBreakerName, State state) {
        CircuitBreaker circuitBreaker = registry.circuitBreaker(circuitBreakerName);
        assertEquals(state, circuitBreaker.getState());
    }
}

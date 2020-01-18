package com.tdevilleduc.urthehero.back.service;

import com.tdevilleduc.urthehero.back.AbstractTest;
import com.tdevilleduc.urthehero.back.exceptions.StoryNotFoundException;
import com.tdevilleduc.urthehero.back.model.Story;
import com.tdevilleduc.urthehero.back.service.impl.StoryService;
import io.github.resilience4j.circuitbreaker.CircuitBreaker.State;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.vavr.collection.Stream;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest
class StoryServiceTest extends AbstractTest {

    @Autowired
    private StoryService storyService;

    @Test
    void exists_thenCorrect() {
        Integer storyId = 1;
        boolean exists = storyService.exists(storyId);
        Assertions.assertTrue(exists);
    }

    @Test
    void notExists_thenCorrect() {
        Integer storyId = 41;
        boolean notExists = storyService.notExists(storyId);
        Assertions.assertTrue(notExists);
    }

    @Test
    void findByPageId_thenCorrect() {
        Integer storyId = 1;
        Optional<Story> optional = storyService.findById(storyId);

        Assertions.assertTrue(optional.isPresent());
        Story story = optional.get();
        Assertions.assertEquals(Integer.valueOf(1), story.getStoryId());
        Assertions.assertEquals("Ulysse", story.getTitle());
        Assertions.assertEquals(Integer.valueOf(1), story.getAuthorId());
        Assertions.assertEquals(Integer.valueOf(1), story.getFirstPageId());
        Assertions.assertEquals(Long.valueOf(3), story.getNumberOfReaders());
    }

    @Test
    void findById_withIdNull() {
        // with resilience4j, IllegalArgumentException is catched and fallback method 'emptyStory' is called
//        Assertions.assertThrows(IllegalArgumentException.class, () -> storyService.findById(null));
        assertEquals(Optional.empty(), storyService.findById(null));
    }
}

package com.tdevilleduc.urthehero.back.service;

import com.tdevilleduc.urthehero.back.BackApplication;
import com.tdevilleduc.urthehero.back.model.Story;
import com.tdevilleduc.urthehero.back.service.impl.StoryService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = BackApplication.class)
public class StoryServiceTest {

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
        Story story = storyService.findById(storyId);

        Assertions.assertNotNull(story);
        Assertions.assertEquals(Integer.valueOf(1), story.getId());
        Assertions.assertEquals("Ulysse", story.getTitle());
        Assertions.assertEquals(Integer.valueOf(1), story.getAuthorId());
        Assertions.assertEquals(Integer.valueOf(1), story.getFirstPageId());
        Assertions.assertEquals(Long.valueOf(4), story.getNumberOfPages());
        Assertions.assertEquals(Long.valueOf(3), story.getNumberOfReaders());
        Assertions.assertEquals(4, story.getPages().size());
    }

}

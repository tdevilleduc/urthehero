package com.tdevilleduc.urthehero.back.service;

import com.tdevilleduc.urthehero.back.BackApplication;
import com.tdevilleduc.urthehero.back.model.Story;
import com.tdevilleduc.urthehero.back.service.impl.PageService;
import com.tdevilleduc.urthehero.back.service.impl.StoryService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = BackApplication.class)
public class StoryServiceTest {

    @Autowired
    private StoryService storyService;

    @Test
    public void test_exists_thenCorrect() {
        Integer storyId = 1;
        boolean exists = storyService.exists(storyId);
        Assert.assertTrue(exists);
    }

    @Test
    public void test_notExists_thenCorrect() {
        Integer storyId = 41;
        boolean notExists = storyService.notExists(storyId);
        Assert.assertTrue(notExists);
    }

    @Test
    public void test_findByPageId_thenCorrect() {
        Integer storyId = 1;
        Story story = storyService.findById(storyId);

        Assert.assertNotNull(story);
        Assert.assertEquals(Integer.valueOf(1), story.getId());
        Assert.assertEquals("Ulysse", story.getTitle());
        Assert.assertEquals(Integer.valueOf(1), story.getAuthorId());
        Assert.assertEquals(Integer.valueOf(1), story.getFirstPageId());
        Assert.assertEquals(Integer.valueOf(4), story.getNumberOfPages());
        Assert.assertEquals(Integer.valueOf(3), story.getNumberOfReaders());
        Assert.assertEquals(4, story.getPages().size());
    }

}

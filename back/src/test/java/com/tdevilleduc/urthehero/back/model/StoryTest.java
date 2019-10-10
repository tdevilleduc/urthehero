package com.tdevilleduc.urthehero.back.model;

import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;

public class StoryTest {

    @Test
    public void test_Constructor() {
        Integer storyId = 67;
        String storyTitle = "test de titre";
        Integer storyAuthorId = 23;
        Integer storyFirstPageId = 98;
        Story story = new Story(storyId, storyTitle, storyAuthorId, storyFirstPageId);

        Assert.assertEquals(story.getTitle(), storyTitle);
        Assert.assertEquals(story.getAuthorId(), storyAuthorId);
        Assert.assertEquals(story.getFirstPageId(), storyFirstPageId);
        Assert.assertEquals(Integer.valueOf(0), story.getCurrentPageId());
        Assert.assertTrue(story.getPages().isEmpty());

        Story secondStory = new Story();
        secondStory.setId(storyId);
        secondStory.setTitle(storyTitle);
        secondStory.setAuthorId(storyAuthorId);
        secondStory.setFirstPageId(storyFirstPageId);
        secondStory.setPages(Collections.EMPTY_LIST);

        Assert.assertEquals(secondStory.toString(), story.toString());
        Assert.assertEquals(secondStory, story);
    }
}

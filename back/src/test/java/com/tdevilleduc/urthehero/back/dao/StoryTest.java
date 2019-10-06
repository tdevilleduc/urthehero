package com.tdevilleduc.urthehero.back.dao;

import com.tdevilleduc.urthehero.back.model.Story;
import org.junit.Assert;
import org.junit.Test;

public class StoryTest {

    @Test
    public void test_Constructor() {
        String storyTitle = "test de titre";
        Integer storyAuthorId = 23;
        Integer storyFirstPageId = 98;
        Story story = new Story(storyTitle, storyAuthorId, storyFirstPageId);
        Assert.assertEquals(story.getTitle(), storyTitle);
        Assert.assertEquals(story.getAuthorId(), storyAuthorId);
        Assert.assertEquals(story.getFirstPageId(), storyFirstPageId);
        Assert.assertNull(story.getCurrentPageId());

        Story secondStory = new Story();
        secondStory.setTitle(storyTitle);
        secondStory.setAuthorId(storyAuthorId);
        secondStory.setFirstPageId(storyFirstPageId);
        Assert.assertEquals(secondStory.toString(), story.toString());
        Assert.assertEquals(secondStory, story);
    }
}

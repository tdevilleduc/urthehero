package com.tdevilleduc.urthehero.back.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;

public class StoryTest {

    @Test
    public void test_Constructor() {
        Integer storyId = 67;
        String storyTitle = "test de titre";
        Integer storyAuthorId = 23;
        Integer storyFirstPageId = 98;
        String storyDetailedText = "Tintin long tintin";
        String storyImage = "/chemin/to/image";
        Story story = new Story(storyId, storyTitle, storyAuthorId, storyFirstPageId, storyDetailedText, storyImage);

        Assertions.assertEquals(story.getTitle(), storyTitle);
        Assertions.assertEquals(story.getAuthorId(), storyAuthorId);
        Assertions.assertEquals(story.getFirstPageId(), storyFirstPageId);
        Assertions.assertEquals(story.getDetailedText(), storyDetailedText);
        Assertions.assertEquals(story.getImage(), storyImage);
        Assertions.assertNull(story.getCurrentPageId());
        Assertions.assertTrue(story.getPages().isEmpty());

        Story secondStory = new Story();
        secondStory.setStoryId(storyId);
        secondStory.setTitle(storyTitle);
        secondStory.setAuthorId(storyAuthorId);
        secondStory.setFirstPageId(storyFirstPageId);
        secondStory.setDetailedText(storyDetailedText);
        secondStory.setImage(storyImage);
        secondStory.setPages(Collections.emptyList());

        Assertions.assertEquals(secondStory.toString(), story.toString());
        Assertions.assertEquals(secondStory, story);
    }
}

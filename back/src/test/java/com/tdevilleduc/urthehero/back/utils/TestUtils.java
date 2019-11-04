package com.tdevilleduc.urthehero.back.utils;

import com.tdevilleduc.urthehero.back.model.Story;
import org.testcontainers.shaded.org.apache.commons.lang.RandomStringUtils;

import java.util.Random;

public class TestUtils {

    private static Random random = new Random();

    public static Story createRandomStory() {
        Integer storyId = random.nextInt();
        String storyTitle = RandomStringUtils.random(20);
        Integer storyAuthorId = random.nextInt();
        Integer storyFirstPageId = random.nextInt();
        String storyDetailedText = RandomStringUtils.random(20);
        String storyImage = RandomStringUtils.random(20);
        return new Story(storyId, storyTitle, storyAuthorId, storyFirstPageId,storyDetailedText, storyImage);
    }

    public static Story createStory(Integer authorId, Integer firstPageId) {
        Integer storyId = random.nextInt();
        String storyTitle = RandomStringUtils.random(20);
        String storyDetailedText = RandomStringUtils.random(20);
        String storyImage = RandomStringUtils.random(20);
        return new Story(storyId, storyTitle, authorId, firstPageId,storyDetailedText, storyImage);
    }
}

package com.tdevilleduc.urthehero.back.model;

import com.tdevilleduc.urthehero.back.utils.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PageTest {

    @Test
    public void test_Constructor() {
        Integer pageId = 46;
        String pageText = "test de titre";
        String pageImage = "image de ouf";
        Story story = TestUtils.createRandomStory();
        Page page = new Page(pageId, story, pageText, pageImage, null);

        Assertions.assertEquals(page.getId(), pageId);
        Assertions.assertEquals(page.getText(), pageText);
        Assertions.assertEquals(page.getImage(), pageImage);
        // TODO: make nextPageList not null
        Assertions.assertNull(page.getNextPageList());

        Page secondPage = new Page();
        secondPage.setId(pageId);
        secondPage.setText(pageText);
        secondPage.setStory(story);
        secondPage.setImage(pageImage);

        Assertions.assertEquals(secondPage.toString(), page.toString());
        Assertions.assertEquals(secondPage, page);
    }
}

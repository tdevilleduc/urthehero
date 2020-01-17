package com.tdevilleduc.urthehero.back.model;

import com.tdevilleduc.urthehero.back.utils.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PageTest {

    @Test
    void test_Constructor() {
        Integer pageId = 46;
        String pageText = "test de titre";
        String pageImage = "image de ouf";
        Story story = TestUtils.createRandomStory();
        Page page = new Page(pageId, pageText, pageImage);

        Assertions.assertEquals(page.getId(), pageId);
        Assertions.assertEquals(page.getText(), pageText);
        Assertions.assertEquals(page.getImage(), pageImage);
        Assertions.assertTrue(page.getNextPageList().isEmpty());

        Page secondPage = new Page();
        secondPage.setId(pageId);
        secondPage.setText(pageText);
        secondPage.setImage(pageImage);

        Assertions.assertEquals(secondPage.toString(), page.toString());
        Assertions.assertEquals(secondPage, page);
    }
}

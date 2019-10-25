package com.tdevilleduc.urthehero.back.service;

import com.tdevilleduc.urthehero.back.AbstractTest;
import com.tdevilleduc.urthehero.back.BackApplication;
import com.tdevilleduc.urthehero.back.exceptions.PageNotFoundException;
import com.tdevilleduc.urthehero.back.model.NextPage;
import com.tdevilleduc.urthehero.back.model.Page;
import com.tdevilleduc.urthehero.back.model.Position;
import com.tdevilleduc.urthehero.back.model.Story;
import com.tdevilleduc.urthehero.back.service.impl.PageService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = BackApplication.class)
public class PageServiceTest extends AbstractTest {

    @Autowired
    private PageService pageService;

    @Test
    public void test_findById_thenCorrect() {
        Integer pageId = 1;
        Optional<Page> optional = pageService.findById(pageId);
        Assertions.assertTrue(optional.isPresent());
        Page page = optional.get();

        Assertions.assertNotNull(page);
        Assertions.assertEquals(pageId, page.getId());
        Assertions.assertEquals("image3", page.getImage());
        Assertions.assertEquals("Ulysse", page.getText());

        Story story = page.getStory();
        Assertions.assertNotNull(story);
        Assertions.assertEquals(Integer.valueOf(1), story.getId());

        List<NextPage> nextPageList = page.getNextPageList();
        Assertions.assertNotNull(nextPageList);
        Assertions.assertFalse(nextPageList.isEmpty());
        Assertions.assertEquals(3, nextPageList.size());

        NextPage nextPage1 = nextPageList.get(0);
        Assertions.assertNotNull(nextPage1);
        Assertions.assertEquals(Integer.valueOf(1), nextPage1.getId());
        Assertions.assertEquals(Integer.valueOf(2), nextPage1.getDestinationPageId());
        Assertions.assertEquals(Integer.valueOf(1), nextPage1.getPageId());
        Assertions.assertEquals("gauche", nextPage1.getText());
        Assertions.assertEquals(Position.LEFT, nextPage1.getPosition());

        NextPage nextPage2 = nextPageList.get(1);
        Assertions.assertNotNull(nextPage2);
        Assertions.assertEquals(Integer.valueOf(2), nextPage2.getId());
        Assertions.assertEquals(Integer.valueOf(3), nextPage2.getDestinationPageId());
        Assertions.assertEquals(Integer.valueOf(1), nextPage2.getPageId());
        Assertions.assertEquals("droite", nextPage2.getText());
        Assertions.assertEquals(Position.RIGHT, nextPage2.getPosition());

        NextPage nextPage3 = nextPageList.get(2);
        Assertions.assertNotNull(nextPage3);
        Assertions.assertEquals(Integer.valueOf(3), nextPage3.getId());
        Assertions.assertEquals(Integer.valueOf(8), nextPage3.getDestinationPageId());
        Assertions.assertEquals(Integer.valueOf(1), nextPage3.getPageId());
        Assertions.assertEquals("centre", nextPage3.getText());
        Assertions.assertEquals(Position.CENTER, nextPage3.getPosition());

    }

    @Test
    public void test_findById_thenNotFound() {
        Integer pageId = 13;
        Optional<Page> optional = pageService.findById(pageId);
        Assertions.assertTrue(optional.isEmpty());
    }

    @Test
    public void test_findById_withIdNull() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> pageService.findById(null));
    }

    @Test
    public void delete_thenNotFound() {
        Integer pageId = 13;
        Assertions.assertThrows(PageNotFoundException.class, () -> pageService.delete(pageId));
    }

}

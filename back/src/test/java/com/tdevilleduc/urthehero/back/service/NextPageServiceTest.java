package com.tdevilleduc.urthehero.back.service;

import com.tdevilleduc.urthehero.back.exceptions.PageNotFoundException;
import com.tdevilleduc.urthehero.back.model.NextPage;
import com.tdevilleduc.urthehero.back.model.Position;
import com.tdevilleduc.urthehero.back.service.impl.NextPageService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class NextPageServiceTest {

    private NextPageService nextPageService;

    @Before
    public void onSetupClass() {
        nextPageService = new NextPageService();
    }

    @After
    public void onTeardownClass() {
        nextPageService = null;
    }

    @Test
    public void test_findByPageId_thenCorrect() {
        Integer pageId = Integer.valueOf(1);
        List<NextPage> nextPageList = nextPageService.findByPageId(pageId);
        Assert.assertNotNull(nextPageList);
        Assert.assertFalse(nextPageList.isEmpty());
        Assert.assertEquals(3, nextPageList.size());

        NextPage nextPage1 = nextPageList.get(0);
        Assert.assertNotNull(nextPage1);
        Assert.assertEquals(Integer.valueOf(1), nextPage1.getId());
        Assert.assertEquals(Integer.valueOf(2), nextPage1.getDestinationPageId());
        Assert.assertEquals(Integer.valueOf(1), nextPage1.getPageId());
        Assert.assertEquals("gauche", nextPage1.getText());
        Assert.assertEquals(Position.LEFT, nextPage1.getPosition());

        NextPage nextPage2 = nextPageList.get(1);
        Assert.assertNotNull(nextPage2);
        Assert.assertEquals(Integer.valueOf(2), nextPage2.getId());
        Assert.assertEquals(Integer.valueOf(3), nextPage2.getDestinationPageId());
        Assert.assertEquals(Integer.valueOf(1), nextPage2.getPageId());
        Assert.assertEquals("droite", nextPage2.getText());
        Assert.assertEquals(Position.RIGHT, nextPage2.getPosition());

        NextPage nextPage3 = nextPageList.get(2);
        Assert.assertNotNull(nextPage3);
        Assert.assertEquals(Integer.valueOf(3), nextPage3.getId());
        Assert.assertEquals(Integer.valueOf(8), nextPage3.getDestinationPageId());
        Assert.assertEquals(Integer.valueOf(1), nextPage3.getPageId());
        Assert.assertEquals("centre", nextPage3.getText());
        Assert.assertEquals(Position.CENTER, nextPage3.getPosition());

    }

    @Test(expected = PageNotFoundException.class)
    public void test_findByPageId_thenNotFound() {
        Integer pageId = Integer.valueOf(13);
        nextPageService.findByPageId(pageId);
    }

}

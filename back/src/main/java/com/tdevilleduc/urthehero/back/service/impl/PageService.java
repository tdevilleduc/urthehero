package com.tdevilleduc.urthehero.back.service.impl;

import com.tdevilleduc.urthehero.back.dao.PageDao;
import com.tdevilleduc.urthehero.back.exceptions.PageInternalErrorException;
import com.tdevilleduc.urthehero.back.model.NextPage;
import com.tdevilleduc.urthehero.back.model.Page;
import com.tdevilleduc.urthehero.back.service.INextPageService;
import com.tdevilleduc.urthehero.back.service.IPageService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class PageService implements IPageService {

    @Autowired
    private INextPageService nextPageService;

    @Autowired
    private PageDao pageDao;

    public boolean exists(Integer pageId) {
        Assert.notNull(pageId, "The pageId parameter is mandatory !");
        Optional<Page> page = pageDao.findById(pageId);
        if (page.isEmpty()) {
            log.error("La page avec l'id {} n'existe pas", pageId);
            return false;
        }
        return true;
    }

    public boolean notExists(Integer pageId) {
        return ! exists(pageId);
    }

    public Optional<Page> findById(Integer pageId) {
        Assert.notNull(pageId, "The pageId parameter is mandatory !");
        return pageDao.findById(pageId)
                .map(this::fillPageWithNextPages);
    }

    public List<Page> findAll() {
        return pageDao.findAll();
    }

    private Page fillPageWithNextPages(Page page) {
        List<NextPage> nextPageList = nextPageService.findByPageId(page.getId());
        page.setNextPageList(nextPageList);
        return page;
    }

    public Page createOrUpdate(Page page) {
        return pageDao.save(page);
    }

    public void delete(Integer pageId) {
        Assert.notNull(pageId, "The pageId parameter is mandatory !");
        Optional<Page> optional = findById(pageId);
        optional
            .ifPresentOrElse(page -> pageDao.delete(page),
                () -> {
                    throw new PageInternalErrorException(MessageFormatter.format("La page avec l'id {} n'existe pas", pageId).getMessage());
                }
        );
    }
}

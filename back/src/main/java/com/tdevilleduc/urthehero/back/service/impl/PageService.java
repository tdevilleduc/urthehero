package com.tdevilleduc.urthehero.back.service.impl;

import com.tdevilleduc.urthehero.back.dao.PageDao;
import com.tdevilleduc.urthehero.back.exceptions.PageNotFoundException;
import com.tdevilleduc.urthehero.back.model.NextPage;
import com.tdevilleduc.urthehero.back.model.Page;
import com.tdevilleduc.urthehero.back.service.INextPageService;
import com.tdevilleduc.urthehero.back.service.IPageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public Page findById(Integer pageId) {
        Optional<Page> optionalPage = pageDao.findById(pageId);
        if (optionalPage.isEmpty()) {
            log.error("La page avec l'id {} n'existe pas", pageId);
            throw new PageNotFoundException(String.format("La page avec l'id {} n'existe pas", pageId));
        }

        Page page = optionalPage.get();

        List<NextPage> nextPageList = nextPageService.findByPageId(pageId);
        page.setNextPageList(nextPageList);

        return page;
    }

    public List<Page> findAll() {
        return pageDao.findAll();
    }

    public Page createOrUpdate(Page page) {
        return pageDao.save(page);
    }

    public void delete(Integer pageId) {
        Page page = findById(pageId);
        pageDao.delete(page);
    }
}

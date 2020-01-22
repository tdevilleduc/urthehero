package com.tdevilleduc.urthehero.back.service.impl;

import com.tdevilleduc.urthehero.back.dao.PageDao;
import com.tdevilleduc.urthehero.back.exceptions.PageNotFoundException;
import com.tdevilleduc.urthehero.back.model.NextPage;
import com.tdevilleduc.urthehero.back.model.Page;
import com.tdevilleduc.urthehero.back.model.PageDTO;
import com.tdevilleduc.urthehero.back.service.INextPageService;
import com.tdevilleduc.urthehero.back.service.IPageService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.tdevilleduc.urthehero.back.config.ResilienceConstants.INSTANCE_PAGE_SERVICE;

@Slf4j
@Service
public class PageService implements IPageService {

    @Autowired
    private INextPageService nextPageService;
    @Autowired
    private PageDao pageDao;

    @CircuitBreaker(name = INSTANCE_PAGE_SERVICE, fallbackMethod = "notExists")
    public boolean exists(final Integer pageId) {
        Assert.notNull(pageId, "The pageId parameter is mandatory !");
        Optional<Page> page = pageDao.findById(pageId);
        if (page.isEmpty()) {
            log.error("La page avec l'id {} n'existe pas", pageId);
            return false;
        }
        return true;
    }

    @CircuitBreaker(name = INSTANCE_PAGE_SERVICE, fallbackMethod = "notExists")
    public boolean notExists(final Integer pageId) {
        return ! exists(pageId);
    }

    @SuppressWarnings({"squid:UnusedPrivateMethod", "squid:S1172"})
    private boolean notExists(final Integer pageId, final Throwable e) {
        return false;
    }

    @CircuitBreaker(name = INSTANCE_PAGE_SERVICE, fallbackMethod = "emptyPage")
    public Optional<Page> findById(final Integer pageId) {
        Assert.notNull(pageId, "The pageId parameter is mandatory !");
        return pageDao.findById(pageId)
                .map(this::fillPageWithNextPages);
    }

    @SuppressWarnings({"squid:UnusedPrivateMethod", "squid:S1172"})
    private Optional<Page> emptyPage(final Integer pageId, final Throwable e) {
        log.error("Unable to retrieve page with id {}", pageId, e);
        return Optional.empty();
    }

    @CircuitBreaker(name = INSTANCE_PAGE_SERVICE, fallbackMethod = "emptyPageList")
    public List<Page> findAll() {
        return pageDao.findAll();
    }

    @SuppressWarnings({"squid:UnusedPrivateMethod", "squid:S1172"})
    private List<Page> emptyPageList(final Throwable e) {
        log.error("Unable to retrieve page list", e);
        return Collections.emptyList();
    }

    private Page fillPageWithNextPages(Page page) {
        List<NextPage> nextPageList = nextPageService.findByPageId(page.getId());
        page.setNextPageList(nextPageList);
        return page;
    }

    public PageDTO createOrUpdate(PageDTO pageDTO) {
        Page page = new Page();
        page.setId(pageDTO.getId());
        page.setImage(pageDTO.getImage());
        page.setText(pageDTO.getText());
        Page newPage = pageDao.save(page);
        PageDTO newPageDTO = new PageDTO();
        newPageDTO.setId(newPage.getId());
        newPageDTO.setImage(newPage.getImage());
        newPageDTO.setText(newPage.getText());
        return newPageDTO;
    }

    public void delete(Integer pageId) {
        Assert.notNull(pageId, "The pageId parameter is mandatory !");
        Optional<Page> optional = findById(pageId);
        optional
            .ifPresentOrElse(page -> pageDao.delete(page),
                () -> {
                    throw new PageNotFoundException(MessageFormatter.format("La page avec l'id {} n'existe pas", pageId).getMessage());
                }
        );
    }
}

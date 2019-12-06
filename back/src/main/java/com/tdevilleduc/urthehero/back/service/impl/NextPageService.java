package com.tdevilleduc.urthehero.back.service.impl;

import com.tdevilleduc.urthehero.back.dao.NextPageDao;
import com.tdevilleduc.urthehero.back.exceptions.PageNotFoundException;
import com.tdevilleduc.urthehero.back.model.NextPage;
import com.tdevilleduc.urthehero.back.service.INextPageService;
import com.tdevilleduc.urthehero.back.service.IPageService;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NextPageService implements INextPageService {

    @Autowired
    private IPageService pageService;
    @Autowired
    private NextPageDao nextPageDao;

    public List<NextPage> findByPageId(Integer pageId) {
        if (pageService.notExists(pageId)) {
            throw new PageNotFoundException(MessageFormatter.format("La page avec l'id {} n'existe pas", pageId).getMessage());
        }

        return nextPageDao.findByPageId(pageId);
    }
}

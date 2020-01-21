package com.tdevilleduc.urthehero.back.service;

import com.tdevilleduc.urthehero.back.model.Page;
import com.tdevilleduc.urthehero.back.model.PageDTO;

import java.util.Optional;

public interface IPageService {

    boolean exists(Integer pageId);
    boolean notExists(Integer pageId);
    Optional<Page> findById(Integer pageId);
    PageDTO createOrUpdate(PageDTO page);
    void delete(Integer pageId);
}

package com.tdevilleduc.urthehero.back.service;

import com.tdevilleduc.urthehero.back.model.Page;

import java.util.List;
import java.util.Optional;

public interface IPageService {

    boolean exists(Integer pageId);
    boolean notExists(Integer pageId);
    Optional<Page> findById(Integer pageId);
    Page createOrUpdate(Page page);
    void delete(Integer pageId);
}

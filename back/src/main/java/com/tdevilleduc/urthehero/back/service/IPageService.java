package com.tdevilleduc.urthehero.back.service;

import com.tdevilleduc.urthehero.back.model.Page;

import java.util.List;

public interface IPageService {

    boolean exists(Integer pageId);
    boolean notExists(Integer pageId);
    Page findById(Integer pageId);
    Page createOrUpdate(Page page);
    void delete(Integer pageId);
}

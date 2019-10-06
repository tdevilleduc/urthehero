package com.tdevilleduc.urthehero.back.service;

import com.tdevilleduc.urthehero.back.model.Page;

import java.util.List;

public interface IPageService {

    public boolean exists(Integer pageId);
    public boolean notExists(Integer pageId);
    public Page findById(Integer pageId);
    public List<Page> findAll();
}

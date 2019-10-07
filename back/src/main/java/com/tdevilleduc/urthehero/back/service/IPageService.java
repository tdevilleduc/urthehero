package com.tdevilleduc.urthehero.back.service;

import com.tdevilleduc.urthehero.back.model.Page;

public interface IPageService {

    boolean exists(Integer pageId);
    boolean notExists(Integer pageId);
    Page findById(Integer pageId);

}

package com.tdevilleduc.urthehero.back.service;

import com.tdevilleduc.urthehero.back.model.NextPage;

import java.util.List;

public interface INextPageService {

    public List<NextPage> findByPageId(Integer pageId);

}
package com.tdevilleduc.urthehero.back.service;

import com.tdevilleduc.urthehero.back.model.Story;

import java.util.List;

public interface IStoryService {

    boolean exists(Integer storyId);
    boolean notExists(Integer storyId);
    Story findById(Integer storyId);
    List<Story> findAll();
}

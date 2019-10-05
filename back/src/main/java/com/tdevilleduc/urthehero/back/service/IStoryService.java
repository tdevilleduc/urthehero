package com.tdevilleduc.urthehero.back.service;

import com.tdevilleduc.urthehero.back.model.Story;

import java.util.List;

public interface IStoryService {

    public boolean exists(Integer storyId);
    public boolean notExists(Integer storyId);
    public Story findById(Integer storyId);
    public List<Story> findAll();
}

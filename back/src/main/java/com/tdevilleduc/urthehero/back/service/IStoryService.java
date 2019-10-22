package com.tdevilleduc.urthehero.back.service;

import com.tdevilleduc.urthehero.back.model.Story;

import java.util.List;
import java.util.Optional;

public interface IStoryService {

    boolean exists(Integer storyId);
    boolean notExists(Integer storyId);
    Optional<Story> findById(Integer storyId);
    List<Story> findAll();
    List<Story> findByPersonId(Integer personId);
    Story createOrUpdate(Story story);
    void delete(Integer storyId);
}

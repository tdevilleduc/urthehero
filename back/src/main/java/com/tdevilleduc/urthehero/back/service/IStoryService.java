package com.tdevilleduc.urthehero.back.service;

import com.tdevilleduc.urthehero.back.model.Story;
import com.tdevilleduc.urthehero.back.model.StoryDTO;

import java.util.List;
import java.util.Optional;

public interface IStoryService {

    boolean exists(Integer storyId);
    boolean notExists(Integer storyId);
    Optional<Story> findById(Integer storyId);
    List<Story> findAll();
    List<Story> findByPersonId(Integer personId);
    StoryDTO createOrUpdate(StoryDTO storyDto);
    void delete(Integer storyId);
}

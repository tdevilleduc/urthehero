package com.tdevilleduc.urthehero.back.service;

import com.tdevilleduc.urthehero.back.model.Progression;

import java.util.List;
import java.util.Optional;

public interface IProgressionService {

    Progression doProgressionAction(Integer personId, Integer storyId, Integer newPageId);
    List<Progression> findByPersonId(Integer personId);
    Optional<Progression> findByPersonIdAndStoryId(Integer personId, Integer storyId);
    Long countByStoryId(Integer storyId);

}

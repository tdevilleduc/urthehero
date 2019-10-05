package com.tdevilleduc.urthehero.back.service;

import com.tdevilleduc.urthehero.back.model.Progression;

public interface IProgressionService {

    Progression doProgressionAction(Integer personId, Integer storyId, Integer newPageId);

}

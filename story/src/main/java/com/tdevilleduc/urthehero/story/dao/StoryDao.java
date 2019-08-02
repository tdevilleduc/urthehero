package com.tdevilleduc.urthehero.story.dao;

import com.tdevilleduc.urthehero.story.model.Page;
import com.tdevilleduc.urthehero.story.model.Story;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoryDao extends JpaRepository<Story, Integer> {

    Story findById(int id);
}

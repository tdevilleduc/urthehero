package com.tdevilleduc.urthehero.story.dao;

import com.tdevilleduc.urthehero.story.model.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PageDao extends JpaRepository<Page, Integer> {

    Page findById(int id);
    // List<Page> findByStoryId(int storyId);
}

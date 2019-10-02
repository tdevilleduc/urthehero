package com.tdevilleduc.urthehero.back.dao;

import com.tdevilleduc.urthehero.model.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PageDao extends JpaRepository<Page, Integer> {

    Page findById(int id);
    // List<Page> findByStoryId(int storyId);
}

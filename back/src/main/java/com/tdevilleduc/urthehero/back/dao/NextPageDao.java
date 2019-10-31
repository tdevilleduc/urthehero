package com.tdevilleduc.urthehero.back.dao;

import com.tdevilleduc.urthehero.back.model.NextPage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NextPageDao extends JpaRepository<NextPage, Integer> {

    List<NextPage> findByPageId(Integer pageId);
}

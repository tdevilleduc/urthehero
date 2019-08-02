package com.tdevilleduc.urthehero.story.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Story {

    @Id
    @GeneratedValue
    private Integer id;
    private String title;
    private Integer authorId;
    private Integer firstPageId;

    public Story() {
    }

    public Story(Integer id, String title, Integer authorId, Integer firstPageId) {
        this.id = id;
        this.title = title;
        this.authorId = authorId;
        this.firstPageId = firstPageId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }

    public Integer getFirstPageId() {
        return firstPageId;
    }

    public void setFirstPageId(Integer firstPageId) {
        this.firstPageId = firstPageId;
    }
}

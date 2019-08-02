package com.tdevilleduc.urthehero.story.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.List;

@Entity
public class Page {

    @Id
    @GeneratedValue
    private Integer id;
    private Integer storyId;

    @Column(columnDefinition = "TEXT")
    private String text;
    private String image;
    //private List<Next> listNext;

    public Page() {
    }

    public Page(Integer id, Integer storyId, String text) {
        this.id = id;
        this.storyId = storyId;
        this.text = text;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStoryId() {
        return storyId;
    }

    public void setStoryId(Integer storyId) {
        this.storyId = storyId;
    }


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}

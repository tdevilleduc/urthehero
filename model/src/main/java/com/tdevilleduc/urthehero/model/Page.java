package com.tdevilleduc.urthehero.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class Page {

    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne
    @JoinColumn
    private Story story;

    @Column(columnDefinition = "TEXT")
    private String text;
    private String image;

    public Page() {
    }

    public Page(Integer id, Story story, String text) {
        this.id = id;
        this.story = story;
        this.text = text;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @JsonIgnore
    public Story getStory() {
        return story;
    }

    public void setStory(Story story) {
        this.story = story;
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

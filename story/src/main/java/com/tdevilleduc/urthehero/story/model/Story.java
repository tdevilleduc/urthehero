package com.tdevilleduc.urthehero.story.model;

import javax.persistence.*;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Entity
public class Story {

    @Id
    @GeneratedValue
    private Integer id;
    private String title;
    private Integer authorId;
    private Integer firstPageId;

    @OneToMany(mappedBy = "story", cascade = CascadeType.ALL)
    private List<Page> pages;

    public Story() {
    }

    public Story(Integer id, String title, Integer authorId, Integer firstPageId, Page... pages) {
        this.id = id;
        this.title = title;
        this.authorId = authorId;
        this.firstPageId = firstPageId;
        this.pages = Stream.of(pages).collect(Collectors.toList());
        this.pages.forEach(x -> x.setStory(this));
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

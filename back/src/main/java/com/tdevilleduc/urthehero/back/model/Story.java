package com.tdevilleduc.urthehero.back.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Entity
public class Story {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private Integer authorId;
    private Integer firstPageId;
    @Transient
    private Integer currentPageId;

    @OneToMany(mappedBy = "story", cascade = CascadeType.ALL)
    private List<Page> pages;

    public Story() {
    }

    public Story(String title, Integer authorId, Integer firstPageId) {
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

    public Integer getCurrentPageId() {
        return currentPageId;
    }

    public void setCurrentPageId(Integer currentPageId) {
        this.currentPageId = currentPageId;
    }

    @JsonIgnore
    public List<Page> getPages() {
        return pages;
    }

    @Override
    public String toString() {
        return "Story{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", authorId=" + authorId +
                ", firstPageId=" + firstPageId +
                ", currentPageId=" + currentPageId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Story story = (Story) o;
        return Objects.equals(id, story.id) &&
                title.equals(story.title) &&
                authorId.equals(story.authorId) &&
                firstPageId.equals(story.firstPageId) &&
                Objects.equals(currentPageId, story.currentPageId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, authorId, firstPageId, currentPageId);
    }
}

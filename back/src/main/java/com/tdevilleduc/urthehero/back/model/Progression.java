package com.tdevilleduc.urthehero.back.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Progression {

    @Id
    @GeneratedValue
    private Integer id;
    private Integer storyId;
    private Integer personId;
    private Integer actualPageId;

    public Progression() {
    }

    public Progression(Integer id, Integer storyId, Integer personId, Integer actualPageId) {
        this.id = id;
        this.storyId = storyId;
        this.personId = personId;
        this.actualPageId = actualPageId;
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

    public Integer getPersonId() {
        return personId;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId;
    }

    public Integer getActualPageId() {
        return actualPageId;
    }

    public void setActualPageId(Integer actualPageId) {
        this.actualPageId = actualPageId;
    }

    @Override
    public String toString() {
        return "Progression{" +
                "id=" + id +
                ", storyId=" + storyId +
                ", personId=" + personId +
                ", actualPageId=" + actualPageId +
                '}';
    }
}

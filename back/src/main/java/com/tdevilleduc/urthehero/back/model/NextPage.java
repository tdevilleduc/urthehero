package com.tdevilleduc.urthehero.back.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity

public class NextPage {

    @Id
    @GeneratedValue
    @JsonIgnore
    private Integer id;

    private String text;
    private Integer pageId;
    private Integer destinationPageId;
    private Position position;

    public NextPage() {
    }

    public NextPage(Integer id, String text, Integer pageId, Integer destinationPageId, Position position) {
        this.id = id;
        this.text = text;
        this.pageId = pageId;
        this.destinationPageId = destinationPageId;
        this.position = position;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getPageId() {
        return pageId;
    }

    public void setPageId(Integer pageId) {
        this.pageId = pageId;
    }

    public Integer getDestinationPageId() {
        return destinationPageId;
    }

    public void setDestinationPageId(Integer destinationPageId) {
        this.destinationPageId = destinationPageId;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}

package com.tdevilleduc.urthehero.back.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Story {

    @Id
    @GeneratedValue
    private Integer id;
    private String title;
    private Integer authorId;
    private Integer firstPageId;
    @Transient
    private Integer currentPageId;

    @OneToMany(mappedBy = "story", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Page> pages;

}

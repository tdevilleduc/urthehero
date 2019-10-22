package com.tdevilleduc.urthehero.back.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Collections;
import java.util.List;

@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Story {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NonNull
    private Integer id;
    @NonNull
    private String title;
    @NonNull
    private Integer authorId;
    @NonNull
    private Integer firstPageId;
    @Transient
    private Integer currentPageId;
    @Transient
    private Long numberOfPages;
    @Transient
    private Long numberOfReaders;

    private String detailedText;

    private String image;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "story", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Page> pages = Collections.emptyList();



}

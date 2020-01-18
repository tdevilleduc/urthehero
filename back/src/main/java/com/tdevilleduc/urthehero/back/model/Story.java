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
    private Integer storyId;
    @NonNull
    private String title;
    @NonNull
    private Integer authorId;
    @NonNull
    private Integer firstPageId;
    @NonNull
    private String detailedText;
    @NonNull
    private String image;

    @Transient
    private Integer currentPageId;
    @Transient
    private Long numberOfReaders;



}

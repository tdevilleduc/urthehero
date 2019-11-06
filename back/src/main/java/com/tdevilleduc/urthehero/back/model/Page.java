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
public class Page {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NonNull
    private Integer id;
    @NonNull
    private String text;
    @NonNull
    private String image;

    @ManyToOne
    @JoinColumn(name = "story_Id", insertable = false, updatable = false)
    @JsonIgnore
    private Story story;

    @Transient
    private List<NextPage> nextPageList = Collections.emptyList();

}

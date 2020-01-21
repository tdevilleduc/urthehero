package com.tdevilleduc.urthehero.back.model;

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

    @Transient
    private List<NextPage> nextPageList = Collections.emptyList();

}

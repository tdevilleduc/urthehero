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
public class Page {

    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne
    @JoinColumn
    @JsonIgnore
    private Story story;

    @Column(columnDefinition = "TEXT")
    private String text;
    private String image;

}

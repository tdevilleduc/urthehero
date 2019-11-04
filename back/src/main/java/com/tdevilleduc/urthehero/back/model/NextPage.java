package com.tdevilleduc.urthehero.back.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class NextPage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    @JsonIgnore
    private Integer id;
    @NotNull
    private String text;
    @NotNull
    private Integer pageId;
    @NotNull
    private Integer destinationPageId;
    @NotNull
    private Position position;

}

package com.tdevilleduc.urthehero.back.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class NextPage {

    @Id
    @GeneratedValue
    @JsonIgnore
    private Integer id;
    private String text;
    private Integer pageId;
    private Integer destinationPageId;
    private Position position;

}

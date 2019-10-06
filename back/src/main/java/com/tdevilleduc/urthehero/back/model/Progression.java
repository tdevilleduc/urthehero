package com.tdevilleduc.urthehero.back.model;

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
public class Progression {

    @Id
    @GeneratedValue
    private Integer id;
    private Integer storyId;
    private Integer personId;
    private Integer actualPageId;

}

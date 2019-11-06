package com.tdevilleduc.urthehero.back.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
@RequiredArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Progression {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private Integer id;
    @NotNull
    private Integer storyId;
    @NotNull
    private Integer personId;
    @NotNull
    private Integer actualPageId;

}

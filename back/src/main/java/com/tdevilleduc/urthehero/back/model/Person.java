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
public class Person {

    @Id
    @NonNull
    @GeneratedValue
    private int id;
    @NonNull
    private String login;
    @NonNull
    private String displayName;
    @NonNull
    private String email;

    @JsonIgnore
    private String password;

}

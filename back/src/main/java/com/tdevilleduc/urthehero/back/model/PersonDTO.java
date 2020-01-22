package com.tdevilleduc.urthehero.back.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class PersonDTO {

    private Integer id;
    private String login;
    private String displayName;
    private String email;
    private String password;

}

package com.tdevilleduc.urthehero.back.model;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class PageDTO implements Serializable {

    private Integer id;
    private String text;
    private String image;}

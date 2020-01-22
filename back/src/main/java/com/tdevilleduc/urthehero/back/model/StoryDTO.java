package com.tdevilleduc.urthehero.back.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class StoryDTO {

    private Integer storyId;
    private String title;
    private Integer authorId;
    private Integer firstPageId;
    private String detailedText;
    private String image;

}

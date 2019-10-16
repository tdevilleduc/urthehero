package com.tdevilleduc.urthehero.back.exceptions;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@NoArgsConstructor
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class StoryInternalErrorException extends RuntimeException {
    public StoryInternalErrorException(String s) {
        super(s);
    }
}
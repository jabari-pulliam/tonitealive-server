package com.tonitealive.server.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class InternalServerErrorException extends RuntimeException {

    public static InternalServerErrorException create() {
        return new InternalServerErrorException();
    }

    private InternalServerErrorException() {
        super();
    }

}

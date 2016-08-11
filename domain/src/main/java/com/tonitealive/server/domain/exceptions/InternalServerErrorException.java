package com.tonitealive.server.domain.exceptions;

public class InternalServerErrorException extends RuntimeException {

    public static InternalServerErrorException create() {
        return new InternalServerErrorException();
    }

    private InternalServerErrorException() {
        super();
    }

}

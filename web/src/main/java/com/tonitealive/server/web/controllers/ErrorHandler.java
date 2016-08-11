package com.tonitealive.server.web.controllers;

import com.tonitealive.server.domain.ErrorCodes;
import com.tonitealive.server.domain.exceptions.InternalServerErrorException;
import com.tonitealive.server.domain.models.ErrorInfo;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorInfo handleInternalServerError(HttpServletRequest request, InternalServerErrorException ex) {
        return ErrorInfo.create(ex.getMessage(), ErrorCodes.INTERNAL_SERVER_ERROR, request.getRequestURL().toString());
    }

}

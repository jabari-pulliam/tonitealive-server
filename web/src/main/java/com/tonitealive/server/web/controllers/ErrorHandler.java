package com.tonitealive.server.web.controllers;

import com.tonitealive.server.domain.ErrorCodes;
import com.tonitealive.server.domain.exceptions.ResourceNotFoundException;
import com.tonitealive.server.domain.models.ErrorInfo;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandler {

//    @ExceptionHandler(InternalServerErrorException.class)
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    public ErrorInfo handleInternalServerError(HttpServletRequest request, InternalServerErrorException ex) {
//        return ErrorInfo.create(ex.getMessage(), ErrorCodes.INTERNAL_SERVER_ERROR, request.getRequestURL().toString());
//    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorInfo handleResourceNotFound(HttpServletRequest request, ResourceNotFoundException ex) {
        return ErrorInfo.create(ex.getMessage(), ErrorCodes.RESOURCE_NOT_FOUND, request.getRequestURL().toString());
    }



}

package com.tonitealive.server.controllers;

import com.tonitealive.server.domain.ErrorCodes;
import com.tonitealive.server.domain.exceptions.InternalServerErrorException;
import com.tonitealive.server.domain.models.ApiErrorModel;
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
    public ApiErrorModel handleInternalServerError(InternalServerErrorException ex) {
        return ApiErrorModel.create(ex.getMessage(), ErrorCodes.INTERNAL_SERVER_ERROR);
    }

}

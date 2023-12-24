package com.kan.identity.web;

import com.kan.identity.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class ResponseExceptionHandler {
    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<Object> handleException(NotFoundException ex, WebRequest request) {
        return new ResponseEntity<>(new Error(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }
}

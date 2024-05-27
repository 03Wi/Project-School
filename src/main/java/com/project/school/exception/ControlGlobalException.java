package com.project.school.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.MethodNotAllowedException;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ControlGlobalException{

    @ExceptionHandler(Exception.class)
    public ResponseEntity<MessageException> handleException(Exception ex, WebRequest path) {

        MessageException messageShow = new MessageException(LocalDateTime.now(), ex.getMessage(), path.getContextPath());
        return new ResponseEntity<>(messageShow,
                org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NoSuchMethodException.class)
    public ResponseEntity<MessageException> handleExceptionNotMethod(Exception ex, WebRequest path) {

        MessageException messageShow = new MessageException(LocalDateTime.now(), ex.getMessage(), path.getContextPath());
        return new ResponseEntity<>(messageShow,
                HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(ModelNotFoundException.class)
    public ResponseEntity<MessageException> handleExceptionNotFound(Exception ex, WebRequest path) {

        MessageException messageShow = new MessageException(LocalDateTime.now(), ex.getMessage(),path.getContextPath());
        return new ResponseEntity<>(messageShow,
                HttpStatus.NOT_FOUND);
    }



}

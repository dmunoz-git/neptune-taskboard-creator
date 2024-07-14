package com.neptune.boards.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class NeptuneBoardsErrorHandler {

    @ExceptionHandler(NeptuneBoardsException.class)
    public final ResponseEntity<NeptuneBoardsExceptionResponse> handleNeptuneBoardsException(NeptuneBoardsException exception, WebRequest webRequest){
        NeptuneBoardsExceptionResponse response = new NeptuneBoardsExceptionResponse(LocalDateTime.now(), exception.getMessage(), webRequest.getDescription(false), exception.getOriginClass(), exception.getStatus());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}

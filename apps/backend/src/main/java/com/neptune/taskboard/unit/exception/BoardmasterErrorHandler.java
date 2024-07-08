package com.neptune.taskboard.unit.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class BoardmasterErrorHandler {

    @ExceptionHandler(BoardmasterException.class)
    public final ResponseEntity<BoardmasterExceptionResponse> handleBoardmasterExecption(BoardmasterException exception, WebRequest webRequest){
        BoardmasterExceptionResponse response = new BoardmasterExceptionResponse(LocalDateTime.now(), exception.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}

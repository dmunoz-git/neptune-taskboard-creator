package com.neptune.boards.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class NeptuneBoardsExceptionResponse {
    private LocalDateTime timestamp;
    private String message;
    private String details;
    private Class<?> originClass;
    private HttpStatus status;
}

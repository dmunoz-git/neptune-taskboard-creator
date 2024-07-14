package com.neptune.boards.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.io.Serial;

@Getter
public class NeptuneBoardsException extends Exception{
    @Serial
    private static final long serialVersionUID=1L;
    private final HttpStatus status;
    private final Class<?> originClass;

    public NeptuneBoardsException(String message, HttpStatus status, Class<?> originClass) {
        super(message);
        this.status = status;
        this.originClass = originClass;
    }

    public NeptuneBoardsException(String message, Throwable cause, HttpStatus status, Class<?> originClass) {
        super(message, cause);
        this.status = status;
        this.originClass = originClass;
    }
}

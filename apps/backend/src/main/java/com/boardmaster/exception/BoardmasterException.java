package com.boardmaster.exception;

import java.io.Serial;

public class BoardmasterException extends Exception{
    @Serial
    private static final long serialVersionUID=1L;

    public BoardmasterException(String message) {
        super(message);
    }

    public BoardmasterException(String message, Throwable cause) {
        super(message, cause);
    }
}

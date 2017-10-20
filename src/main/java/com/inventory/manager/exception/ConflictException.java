package com.inventory.manager.exception;

import org.springframework.http.HttpStatus;

public class ConflictException extends HTTPException {

    private static final long serialVersionUID = 1L;

    public ConflictException(String statusCode) {
        super(HttpStatus.CONFLICT, statusCode);
    }

    public ConflictException(String statusCode, String message) {
        super(HttpStatus.CONFLICT, statusCode, message);
    }

    public ConflictException(String statusCode, String message, Throwable e) {
        super(HttpStatus.CONFLICT, statusCode, message, e);
    }

}

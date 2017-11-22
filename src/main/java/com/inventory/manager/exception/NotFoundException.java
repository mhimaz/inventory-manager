package com.inventory.manager.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends HTTPException {

    private static final long serialVersionUID = 1L;

    public NotFoundException(String statusCode) {
        super(HttpStatus.NOT_FOUND, statusCode);
    }

    public NotFoundException(String statusCode, String message) {
        super(HttpStatus.NOT_FOUND, statusCode, message);
    }

    public NotFoundException(String statusCode, String message, Throwable e) {
        super(HttpStatus.NOT_FOUND, statusCode, message, e);
    }

}

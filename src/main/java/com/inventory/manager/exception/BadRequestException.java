package com.inventory.manager.exception;

import org.springframework.http.HttpStatus;

public class BadRequestException extends HTTPException {

    private static final long serialVersionUID = 1L;

    public BadRequestException(String statusCode) {
        super(HttpStatus.BAD_REQUEST, statusCode);
    }

    public BadRequestException(String statusCode, String message) {
        super(HttpStatus.BAD_REQUEST, statusCode, message);
    }

    public BadRequestException(String statusCode, String message, Throwable e) {
        super(HttpStatus.BAD_REQUEST, statusCode, message, e);
    }

}

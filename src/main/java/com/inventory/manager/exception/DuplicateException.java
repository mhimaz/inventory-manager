package com.inventory.manager.exception;

import org.springframework.http.HttpStatus;

public class DuplicateException extends HTTPException {

    private static final long serialVersionUID = 1L;

    public DuplicateException(String statusCode) {
        super(HttpStatus.CONFLICT, statusCode);
    }

    public DuplicateException(String statusCode, String message) {
        super(HttpStatus.CONFLICT, statusCode, message);
    }

    public DuplicateException(String statusCode, String message, Throwable e) {
        super(HttpStatus.CONFLICT, statusCode, message, e);
    }

}

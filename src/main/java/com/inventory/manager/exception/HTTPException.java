package com.inventory.manager.exception;

import org.springframework.http.HttpStatus;

public class HTTPException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private String statusCode;

	private String statusMessage;

	private HttpStatus httpStatus;

	public HTTPException(HttpStatus httpStatus, String statusCode) {
		super(statusCode);
		this.statusCode = statusCode;
		this.httpStatus = httpStatus;
	}

	public HTTPException(HttpStatus httpStatus, String statusCode, String statusMessage) {
		super(statusCode);
		this.statusCode = statusCode;
		this.httpStatus = httpStatus;
		this.statusMessage = statusMessage;
	}

	public HTTPException(HttpStatus httpStatus, String statusCode, Throwable e) {
		super(statusCode, e);
		this.statusCode = statusCode;
		this.httpStatus = httpStatus;
	}

	public HTTPException(HttpStatus httpStatus, String statusCode, String statusMessage, Throwable e) {
		super(statusCode, e);
		this.statusCode = statusCode;
		this.httpStatus = httpStatus;
		this.statusMessage = statusMessage;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getStatusMessage() {
		return statusMessage;
	}

	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}

}

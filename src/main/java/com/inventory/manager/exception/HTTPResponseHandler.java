package com.inventory.manager.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.TypeMismatchException;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;

public abstract class HTTPResponseHandler {

    private static final String INVALID_SORT_PARAMETER_DESCRIPTION = "Invalid sort parameter";

    private static final String INVALID_SORT_PARAMETER_CODE = "INVALID_SORT_PARAMETER";

    private static final String BAD_REQUEST_DESCRIPTION = "Bad Request";

    private static final String BAD_REQUEST_CODE = "BAD_REQUEST";

    private static final String STATUS_CODE = "status-code";

    private static final String STATUS_MESSAGE = "status-message";

    private static final Logger LOGGER = Logger.getLogger(HTTPResponseHandler.class);

    public void setStatusHeaders(HttpServletResponse response, String code, String message) {
        response.setHeader(STATUS_CODE, code);
        response.setHeader(STATUS_MESSAGE, message);
    }

    public void setStatusHeadersToSuccess(HttpServletResponse response) {
        setStatusHeaders(response, "SUCCESS", "Success");
    }

    @ExceptionHandler(HTTPException.class)
    public void handleHTTPException(HTTPException ex, HttpServletRequest request, HttpServletResponse response) {

        LOGGER.error("HTTPException : " + ex.getMessage(), ex);

        String code = ex.getStatusCode();
        String message = ex.getStatusMessage();

        int status = ex.getHttpStatus().value();

        response.setStatus(status);

        setStatusHeaders(response, code, message);
    }

    /**
     * Handles MethodArgumentNotValidException, HttpMessageNotReadableException or generic Exception
     * 
     * @param ex the thrown Exception
     * @param request the request
     * @param response the response
     */
    @ExceptionHandler(Exception.class)
    public void handleException(Exception ex, HttpServletRequest request, HttpServletResponse response) {

        String code = "";
        String message = "";
        int status = 0;
        Throwable rootCauseMessage = ExceptionUtils.getRootCause(ex);

        LOGGER.error("Exception : " + ex.getMessage(), ex);

        // ex.get

        if (ex instanceof HttpMessageNotReadableException || ex instanceof MissingServletRequestParameterException
                || ex instanceof TypeMismatchException) {

            code = BAD_REQUEST_CODE;

            status = HttpStatus.BAD_REQUEST.value();

            if (rootCauseMessage != null) {
                message = rootCauseMessage.getMessage();
            } else {
                message = BAD_REQUEST_DESCRIPTION;
            }

        } else if (ex instanceof HttpMediaTypeNotSupportedException) {

            code = BAD_REQUEST_CODE;
            message = BAD_REQUEST_DESCRIPTION;
            status = HttpStatus.NOT_ACCEPTABLE.value();

        } else {
            code = "ERROR";
            message = "ERROR";
            status = HttpStatus.INTERNAL_SERVER_ERROR.value();
        }
        response.setStatus(status);

        setStatusHeaders(response, code, message);

    }

    /**
     * Handles PropertyReferenceException
     * 
     * @param ex the thrown Exception
     * @param request the request
     * @param response the response
     */
    @ExceptionHandler(PropertyReferenceException.class)
    public void handlePropertyReferenceException(PropertyReferenceException ex, HttpServletResponse response) {

        LOGGER.error(ex.getMessage(), ex);

        String code = INVALID_SORT_PARAMETER_CODE;
        String message = INVALID_SORT_PARAMETER_DESCRIPTION;
        int status = HttpStatus.BAD_REQUEST.value();

        response.setStatus(status);
        setStatusHeaders(response, code, message);
    }

    /**
     * Handles the exceptions thrown by JSR-303 field validations in DTOs. Usually these are DTO objects validated from @
     * Valid annotation in mvc controllers. This handler method binds the binding result message to the ecity-message
     * field in the response
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public void handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, HttpServletRequest request,
            HttpServletResponse response) {

        LOGGER.error(ex.getMessage(), ex);

        BindingResult br = ex.getBindingResult();

        // Gets only the first error associated with the field
        FieldError fe = br.getFieldError();

        String code = BAD_REQUEST_CODE;
        String message = br.hasFieldErrors() ? fe.getDefaultMessage() : br.hasGlobalErrors() ? br.getGlobalError()
                .getDefaultMessage() : code;

        int status = HttpStatus.BAD_REQUEST.value();

        response.setStatus(status);
        setStatusHeaders(response, code, message);
    }

}

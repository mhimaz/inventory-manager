package com.inventory.manager.util;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;

import com.inventory.manager.exception.HTTPException;

@Component
public class AdapterErrorHandlerUtil {

    private static final String STATUS_MESSAGE = "status-message";

    private static final String STATUS_CODE = "status-code";

    public void handleHTTPStatusCodeException(HTTPException e) throws HTTPException {

        String code = "";
        String message = "";

        try {
            code = e.getStatusCode();
            message = e.getStatusMessage();
        } catch (NullPointerException ex) {

            // status code and status message will be sent as blank in the
            // header
        }

        HttpStatus status = HttpStatus.valueOf(e.getHttpStatus().value());

        throw new HTTPException(status, code, message);
    }

    public void throwInternalServerErrorException() throws HTTPException {
        throw new HTTPException(HttpStatus.INTERNAL_SERVER_ERROR, "ERROR", "Internal Server Error");
    }

    public String getExceptionCode(HttpStatusCodeException e) {

        String code = null;

        try {
            code = e.getResponseHeaders().get(STATUS_CODE).get(0);
        } catch (Exception ex) {
            code = "";
        }

        return code;
    }

    public String getExceptionMessage(HttpStatusCodeException e) {

        String message = null;

        try {
            e.getResponseHeaders().get(STATUS_MESSAGE).get(0);
        } catch (Exception ex) {
            message = "";
        }

        return message;
    }

}

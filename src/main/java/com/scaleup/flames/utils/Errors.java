package com.scaleup.flames.utils;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class Errors {
    public static final String ERROR_CODE_PARAM = "X-Error-Code";
    public static final String ERROR_MESSAGE_PARAM = "X-Error-Message";

    public static void send(HttpStatus status, String errorCode, String errorMessage, HttpServletResponse response) {
        response.setStatus(status.value());
        response.setHeader(ERROR_CODE_PARAM, errorCode);
        response.setHeader(ERROR_MESSAGE_PARAM, errorMessage);
    }

    public static void send(HttpStatus status, String errorMessage, HttpServletResponse response) {
        send(status, status.getReasonPhrase(), errorMessage, response);
    }

    public static boolean isError(HttpClientErrorException e, HttpStatus status, Error errorCode) {
        return e.getStatusCode() != null && e.getStatusCode().equals(status) && (errorCode == null || isError(e, errorCode));
    }

    private static boolean isError(HttpClientErrorException e, Error errorCode) {
        List<String> errorCodeHeaders = e.getResponseHeaders().get(Errors.ERROR_CODE_PARAM);
        return errorCodeHeaders != null && errorCodeHeaders.contains(errorCode.name());
    }


}

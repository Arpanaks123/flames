package com.scaleup.flames.Controller.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AppError {
    private final long timestamp;
    private final String error;
    private final int status;
    private final String message;
    private final String messageCode;
    private final String fieldName;

    public AppError(
            long timestamp,
            String error,
            int status,
            String message,
            String messageCode,
            String fieldName
    ) {
        this.timestamp = timestamp;
        this.error = error;
        this.status = status;
        this.message = message;
        this.messageCode = messageCode;
        this.fieldName = fieldName;
    }

    public static AppError appError(HttpStatus status, String message) {
        return appError(status, status.getReasonPhrase(), message);
    }

    public static AppError appError(HttpStatus status, String error, String message) {
        return new AppError(new Date().getTime(), error, status.value(), message, null, null);
    }

    public static AppError appError(HttpStatus status, String error, String message, String fieldName) {
        return new AppError(new Date().getTime(), error, status.value(), message, null, fieldName);
    }

    public static AppError appError(HttpStatus status, String error, String message, String code, String fieldName) {
        return new AppError(new Date().getTime(), error, status.value(), message, code, fieldName);
    }

    public static AppError appError(AppErrorMessage appErrorMessage, String field) {
        return appError(appErrorMessage, field, HttpStatus.BAD_REQUEST);
    }

    public static AppError appError(AppErrorMessage appErrorMessage, String field, HttpStatus status) {
        return appError(status, Integer.toString(status.value()), appErrorMessage.getMessage(), appErrorMessage.getCode(), field);
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getError() {
        return error;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getMessageCode() {
        return messageCode;
    }

    public String getFieldName() {
        return fieldName;
    }

    @Override
    public String toString() {
        return "AppError{" +
                "timestamp=" + timestamp +
                ", error='" + error + '\'' +
                ", status=" + status +
                ", message='" + message + '\'' +
                ", messageCode='" + messageCode + '\'' +
                ", fieldName='" + fieldName + '\'' +
                '}';
    }
}

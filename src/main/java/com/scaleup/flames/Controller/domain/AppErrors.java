package com.scaleup.flames.Controller.domain;

import com.google.common.collect.Iterables;
import org.springframework.http.HttpStatus;

import java.util.Date;
import java.util.List;

public class AppErrors {
    private static AppError DEFAULT_ERROR = AppError.appError(HttpStatus.BAD_REQUEST, "unknown", "unknown", null);
    private final long timestamp;
    private final List<AppError> errors;
    private final String message;

    public AppErrors(long timestamp, List<AppError> errors) {
        this(timestamp, Iterables.getFirst(errors, DEFAULT_ERROR).getMessage(), errors);
    }

    public AppErrors(long timestamp, String message, List<AppError> errors) {
        this.timestamp = timestamp;
        this.message = message;
        this.errors = errors;
    }
    
    public static AppErrors appErrors(List<AppError> appErrors){
        return new AppErrors(new Date().getTime(), appErrors);
    }

    public long getTimestamp() {
        return timestamp;
    }

    public List<AppError> getErrors() {
        return errors;
    }


    public String getError() {
        return getFirst().getError();
    }

    public int getStatus() {
        return getFirst().getStatus();
    }

    public String getMessage() {
        return message;
    }

    private AppError getFirst() {
        return Iterables.getFirst(errors, DEFAULT_ERROR);
    }

    @Override
    public String toString() {
        return "Errors{" +
                "timestamp=" + timestamp +
                ", errors=" + errors +
                '}';
    }
}

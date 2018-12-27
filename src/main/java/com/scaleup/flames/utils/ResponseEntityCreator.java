package com.scaleup.flames.utils;

import com.google.api.client.util.Lists;
import com.google.common.collect.ImmutableMap;
import com.scaleup.flames.Controller.domain.AppError;
import com.scaleup.flames.Controller.domain.AppErrorMessage;
import com.scaleup.flames.Controller.domain.AppErrors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public class ResponseEntityCreator {
    public static ResponseEntity<?> error(HttpServletResponse response, AppErrorMessage errorMessage, String field) {
        return error(response, errorMessage, field, HttpStatus.BAD_REQUEST);
    }

    public static ResponseEntity<Object> errors(HttpServletResponse response, Map<AppErrorMessage, String> errorsForFields, HttpStatus status){
        List<AppError> appErrors = Lists.newArrayList();
        errorsForFields.forEach((errorMessage, field) -> {
            appErrors.add(AppError.appError(errorMessage, field, status));
        });
        response.setStatus(status.ordinal());
        return new ResponseEntity<Object>(AppErrors.appErrors(appErrors), status);
    }

    public static ResponseEntity<?> error(HttpServletResponse response, AppErrorMessage errorMessage, String field, HttpStatus status) {
        Errors.send(status, errorMessage.getCode(), errorMessage.getMessage(), response);
        return new ResponseEntity<>(AppError.appError(errorMessage, field, status), status);
    }

    public static ResponseEntity<?> success() {
        return new ResponseEntity<Object>(ImmutableMap.of("status", "ok"), HttpStatus.OK);
    }
}

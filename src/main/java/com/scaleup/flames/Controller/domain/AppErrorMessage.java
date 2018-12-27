package com.scaleup.flames.Controller.domain;

public enum AppErrorMessage {
    EMAIL_NOT_VERIFIED("errors.forgotten-password.email-not-verified", "User with given email is not verified."),
    EMAIL_NOT_FOUND("errors.forgotten-password.email-not-found", "User with given email doesn't exist."),
    CHANGE_PASSWORD_INVALID_TOKEN("errors.forgotten-password.invalid-token", "Invalid token."),
    AUTHORIZATION_FAILURE("errors.security.authorization-failed", "Authorization failed."),
    SAME_PASSWORD("errors.change-email.same-password", "New email has to differ from existing one."),
    EMAIL_ALREADY_IN_USE("errors.change-email.email-address-used", "Email already used."),
    NAME_ALREADY_IN_USE("errors.change-name.store-name-used", "Name for store already used in this region."),
    INVALID_PASSWORD("errors.change-email.invalid-password", "Given password doesn't match your existing password"),
    INVALID_DATE_FORMAT("errors.generic.invalid-date-format", "Invalid date format. Use dd/MM/yyyy format!"),
    USER_ALREADY_REGISTERED("errors.registration.already-registered", "User already registered."),
    USER_NOT_ACTIVE("errors.registration.user-not-active","User is not active." ),
    INVALID_USER_CREATION("errors.specsavers.invalid-request","Invalid request, user can not be created." ),
    USER_MUST_CHANGE_PASSWORD("info.globalpassword.must-change", "User must change password"),
    PASSWORD_MUST_DIFFER("errors.change-password.same-password", "New password has to differ from existing one.");
    String message;
    String code;

    AppErrorMessage(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getCode() {
        return code;
    }
}

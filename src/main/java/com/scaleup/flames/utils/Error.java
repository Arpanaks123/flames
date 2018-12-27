package com.scaleup.flames.utils;

public enum Error {

        INVALID_USER(404),
        USER_ALREADY_EXISTS,
        EMAIL_ALREADY_EXISTS,
        USER_IS_NOT_ACTIVE,
        USER_IS_NOT_VERIFIED,
        USER_ALREADY_VERIFIED,
        VALIDATION_ERROR,
        THE_SAME_EMAILS,
        REQUIRED_PARAMETER_NOT_PRESENT,
        USER_INTERESTS_NOT_FOUND;

        private int statusCode;

        private Error() {
            this(400);
        }

        private Error(int statusCode) {
            this.statusCode = statusCode;
        }

        public int getStatusCode() {
            return this.statusCode;
        }
    }


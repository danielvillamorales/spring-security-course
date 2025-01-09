package com.cursos.api.spring_security_course.exception;

public class InvalidPasswordencoder extends RuntimeException{
    public InvalidPasswordencoder() {
    }

    public InvalidPasswordencoder(String message) {
        super(message);
    }

    public InvalidPasswordencoder(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidPasswordencoder(Throwable cause) {
        super(cause);
    }

    public InvalidPasswordencoder(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

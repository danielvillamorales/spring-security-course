package com.cursos.api.spring_security_course.exception;

public class NotFoundException extends RuntimeException{

    public NotFoundException() {
        super("Not found");
    }

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}

package com.cursos.api.spring_security_course.exception;

import com.cursos.api.spring_security_course.dto.ApiError;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleException(Exception e, HttpServletRequest request) {
        ApiError apiError = ApiError.builder()
                .backendMessage(e.getLocalizedMessage())
                .path(request.getRequestURL().toString())
                .method(request.getMethod())
                .message("Internal server error")
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleMethodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request) {
        ApiError apiError = ApiError.builder()
                .backendMessage(e.getLocalizedMessage())
                .path(request.getRequestURL().toString())
                .method(request.getMethod())
                .message("error en la peticion enviada " + e.getAllErrors().stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .collect(Collectors.joining(", ")) )
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiError> handleNotFoundException(NotFoundException e, HttpServletRequest request) {
        ApiError apiError = ApiError.builder()
                .backendMessage(e.getLocalizedMessage())
                .path(request.getRequestURL().toString())
                .method(request.getMethod())
                .message("Not found")
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }
}

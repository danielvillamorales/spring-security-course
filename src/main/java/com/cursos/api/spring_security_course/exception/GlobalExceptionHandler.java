package com.cursos.api.spring_security_course.exception;

import com.cursos.api.spring_security_course.dto.ApiError;
import com.cursos.api.spring_security_course.utils.ApiErrorFactory;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleException(Exception e, HttpServletRequest request) {
        return new ResponseEntity<>(ApiErrorFactory.createApiError("error interno del servidor", e, request),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleMethodArgumentNotValidException(MethodArgumentNotValidException e,
                                                                          HttpServletRequest request) {
        String message = "error en la peticion enviada: " + e.getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(", "));
        return new ResponseEntity<>(ApiErrorFactory.createApiError(message, e, request),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiError> handleNotFoundException(NotFoundException e, HttpServletRequest request) {
        return new ResponseEntity<>(ApiErrorFactory.createApiError("No Se Encontraron Datos", e, request),
                HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(InvalidPasswordencoder.class)
    public ResponseEntity<ApiError> handleInvalidPasswordencoder(InvalidPasswordencoder e, HttpServletRequest request) {
        return new ResponseEntity<>(ApiErrorFactory.createApiError("Error en la contraseña", e, request),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiError> handleAccesDeniedException(AccessDeniedException e, HttpServletRequest request) {
        return new ResponseEntity<>(ApiErrorFactory.createApiError("Acceso denegado, no tienes los permisos " +
                "necesarios para acceder a esta funcion, contacta al administrador", e, request),
                HttpStatus.FORBIDDEN);
    }


}

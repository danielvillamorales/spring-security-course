package com.cursos.api.spring_security_course.config.security.handler;

import com.cursos.api.spring_security_course.dto.ApiError;
import com.cursos.api.spring_security_course.utils.ApiErrorFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {
        ApiError apiError = ApiErrorFactory.createApiError("Acceso denegado, no tienes los permisos " +
                "necesarios para acceder a esta funcion, contacta al administrador", accessDeniedException, request);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.FORBIDDEN.value());
        ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
        response.getWriter().write(mapper.writeValueAsString(apiError));
    }
}

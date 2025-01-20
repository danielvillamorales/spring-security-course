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
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationEntrypoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        ApiError apiError = ApiErrorFactory.createApiError("Acceso denegado, no tienes los permisos " +
                "necesarios para acceder a esta funcion, contacta al administrador", authException, request);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
        response.getWriter().write(mapper.writeValueAsString(apiError));
    }
}

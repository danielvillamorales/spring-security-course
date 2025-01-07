package com.cursos.api.spring_security_course.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ApiError implements Serializable {

    private String backendMessage;
    private String message;
    private LocalDateTime timestamp;
    private String path;
    private String method;
}

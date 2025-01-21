package com.cursos.api.spring_security_course.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShowPermission implements Serializable {

    private long id;

    private String operation;
    private String httpMethod;
    private String module;
    private String role;
}
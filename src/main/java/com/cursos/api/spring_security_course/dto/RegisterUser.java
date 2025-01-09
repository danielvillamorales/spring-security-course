package com.cursos.api.spring_security_course.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class RegisterUser implements Serializable {

    private Long id;
    private String name;
    private String username;
    private String role;
    private String jwt;
}

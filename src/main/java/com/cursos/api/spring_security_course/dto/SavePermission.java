package com.cursos.api.spring_security_course.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SavePermission implements Serializable {

    @NotBlank
    private String role;
    @NotBlank
    private String operation;
}
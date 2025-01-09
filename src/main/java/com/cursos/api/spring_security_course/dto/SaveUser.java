package com.cursos.api.spring_security_course.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class SaveUser implements Serializable {
    @Size(min = 4, max = 200)
    private String name;
    @Size(min = 4, max = 200)
    private String username;
    @Size(min = 8, max = 200)
    private String password;
    @Size(min = 8, max = 200)
    private String repeatedPassword;
}

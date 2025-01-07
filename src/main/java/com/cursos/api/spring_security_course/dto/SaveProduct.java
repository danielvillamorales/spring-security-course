package com.cursos.api.spring_security_course.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class SaveProduct implements Serializable {

        @NotBlank
        private String name;

        @DecimalMin(value = "0.01", message = "El precio debe ser mayor a 0")
        private BigDecimal price;

        @Min(1)
        private Long categoryId;
}

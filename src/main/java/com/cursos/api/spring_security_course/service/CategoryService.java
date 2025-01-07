package com.cursos.api.spring_security_course.service;

import com.cursos.api.spring_security_course.dto.SaveCategory;
import com.cursos.api.spring_security_course.persistance.entity.Category;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface CategoryService {
    Page<Category> findAll(Pageable pageable);

    Category findById(long categoryId);

    Category createOne(@Valid SaveCategory category);

    Category updateOneById(long categoryId, @Valid SaveCategory category);

    Category disabledOneById(long categoryId);
}

package com.cursos.api.spring_security_course.utils;

import com.cursos.api.spring_security_course.dto.SaveCategory;
import com.cursos.api.spring_security_course.persistance.entity.Category;

public final class CategoryFactory {

    private CategoryFactory() {
    }

    public static Category createCategoryOnlyId(long id) {
        return Category.builder()
                .id(id)
                .build();
    }

    public static Category createCategory(SaveCategory category) {
        return Category.builder()
                .name(category.getName())
                .status(Category.CategoryStatus.ENABLED)
                .build();
    }

    public static Category updateCategory(Category category, SaveCategory saveCategory) {
        return Category.builder()
                .id(category.getId())
                .name(saveCategory.getName())
                .status(category.getStatus())
                .build();
    }
}

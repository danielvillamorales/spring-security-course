package com.cursos.api.spring_security_course.service.impl;

import com.cursos.api.spring_security_course.dto.SaveCategory;
import com.cursos.api.spring_security_course.exception.NotFoundException;
import com.cursos.api.spring_security_course.persistance.entity.Category;
import com.cursos.api.spring_security_course.persistance.repository.CategoryRepository;
import com.cursos.api.spring_security_course.service.CategoryService;
import com.cursos.api.spring_security_course.utils.CategoryFactory;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public Page<Category> findAll(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    @Override
    public Category findById(long categoryId) {
        return categoryRepository.findById(categoryId).orElseThrow(() -> new NotFoundException("Category not found "
                + categoryId));
    }

    @Override
    public Category createOne(SaveCategory category) {
        return categoryRepository.save(
                CategoryFactory.createCategory(category)
        );
    }

    @Override
    public Category updateOneById(long categoryId, SaveCategory category) {
        return categoryRepository.save(
                CategoryFactory.updateCategory(
                        findById(categoryId),
                        category
                )
        );
    }

    @Override
    public Category disabledOneById(long categoryId) {
        Category category = findById(categoryId);
        category.setStatus(Category.CategoryStatus.DISABLED);
        return categoryRepository.save(category);
    }
}

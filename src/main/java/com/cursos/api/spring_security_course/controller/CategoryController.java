package com.cursos.api.spring_security_course.controller;

import com.cursos.api.spring_security_course.dto.SaveCategory;
import com.cursos.api.spring_security_course.persistance.entity.Category;
import com.cursos.api.spring_security_course.service.impl.CategoryServiceImpl;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categories")
@AllArgsConstructor
public class CategoryController {

    private final CategoryServiceImpl categoryService;

    @PreAuthorize("hasAuthority('READ_ALL_CATEGORIES')")
    @GetMapping
    public ResponseEntity<Page<Category>> findAll(Pageable pageable) {
        Page<Category> categoryPage = categoryService.findAll(pageable);
        if (categoryPage.hasContent()) {
            return new ResponseEntity<>(categoryPage, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasAuthority('READ_ONE_CATEGORY')")
    @GetMapping("/{categoryId}")
    public ResponseEntity<Category> findById(@PathVariable("categoryId") long categoryId) {
        return new ResponseEntity<>(categoryService.findById(categoryId),HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('CREATE_ONE_CATEGORY')")
    @PostMapping
    public ResponseEntity<Category> createOne(@RequestBody @Valid SaveCategory category) {
        return new ResponseEntity<>(categoryService.createOne(category),HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('UPDATE_ONE_CATEGORY')")
    @PutMapping("/{categoryId}")
    public ResponseEntity<Category> updateOne(@PathVariable long categoryId,
                                             @RequestBody @Valid SaveCategory category) {
        return new ResponseEntity<>(categoryService.updateOneById(categoryId,category),HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('DISABLE_ONE_CATEGORY')")
    @PutMapping("/{categoryId}/disabled")
    public ResponseEntity<Category> disabledOne(@PathVariable long categoryId) {
        return new ResponseEntity<>(categoryService.disabledOneById(categoryId),HttpStatus.OK);
    }

}

package com.cursos.api.spring_security_course.controller;

import com.cursos.api.spring_security_course.dto.SaveCategory;
import com.cursos.api.spring_security_course.dto.SaveProduct;
import com.cursos.api.spring_security_course.persistance.entity.Category;
import com.cursos.api.spring_security_course.persistance.entity.Product;
import com.cursos.api.spring_security_course.service.CategoryService;
import com.cursos.api.spring_security_course.service.ProductService;
import com.cursos.api.spring_security_course.service.impl.CategoryServiceImpl;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categories")
@AllArgsConstructor
public class CategoryController {

    private final CategoryServiceImpl categoryService;

    @GetMapping
    public ResponseEntity<Page<Category>> findAll(Pageable pageable) {
        Page<Category> categoryPage = categoryService.findAll(pageable);
        if (categoryPage.hasContent()) {
            return new ResponseEntity<>(categoryPage, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<Category> findById(@PathVariable("categoryId") long categoryId) {
        return new ResponseEntity<>(categoryService.findById(categoryId),HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Category> createOne(@RequestBody @Valid SaveCategory category) {
        return new ResponseEntity<>(categoryService.createOne(category),HttpStatus.CREATED);
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<Category> updateOne(@PathVariable long categoryId,
                                             @RequestBody @Valid SaveCategory category) {
        return new ResponseEntity<>(categoryService.updateOneById(categoryId,category),HttpStatus.OK);
    }

    @PutMapping("/{categoryId}/disabled")
    public ResponseEntity<Category> disabledOne(@PathVariable long categoryId) {
        return new ResponseEntity<>(categoryService.disabledOneById(categoryId),HttpStatus.OK);
    }

}

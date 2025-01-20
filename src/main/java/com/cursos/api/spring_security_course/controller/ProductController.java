package com.cursos.api.spring_security_course.controller;

import com.cursos.api.spring_security_course.dto.SaveProduct;
import com.cursos.api.spring_security_course.persistance.entity.Product;
import com.cursos.api.spring_security_course.service.impl.ProductServiceImpl;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
@AllArgsConstructor
public class ProductController {

    private final ProductServiceImpl productService;

    @PreAuthorize("hasAnyRole('ADMINISTRATOR','ASSISTANT_ADMINISTRATOR')")
    @GetMapping
    public ResponseEntity<Page<Product>> findAll(Pageable pageable) {
        Page<Product> productPage = productService.findAll(pageable);
        if (productPage.hasContent()) {
            return new ResponseEntity<>(productPage, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasAnyRole('ADMINISTRATOR','ASSISTANT_ADMINISTRATOR')")
    @GetMapping("/{productId}")
    public ResponseEntity<Product> findById(@PathVariable("productId") long productId) {
        return new ResponseEntity<>(productService.findById(productId),HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ASSISTANT_ADMINISTRATOR')")
    @PostMapping
    public ResponseEntity<Product> createOne(@RequestBody @Valid SaveProduct product) {
        return new ResponseEntity<>(productService.createOne(product),HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ADMINISTRATOR','ASSISTANT_ADMINISTRATOR')")
    @PutMapping("/{productId}")
    public ResponseEntity<Product> updateOne(@PathVariable long productId,
                                             @RequestBody @Valid SaveProduct product) {
        return new ResponseEntity<>(productService.updateOneById(productId,product),HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ASSISTANT_ADMINISTRATOR')")
    @PutMapping("/{productId}/disabled")
    public ResponseEntity<Product> disabledOne(@PathVariable long productId) {
        return new ResponseEntity<>(productService.disabledOneById(productId),HttpStatus.OK);
    }

}

package com.cursos.api.spring_security_course.service;

import com.cursos.api.spring_security_course.dto.SaveProduct;
import com.cursos.api.spring_security_course.persistance.entity.Product;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {

    Page<Product> findAll(Pageable pageable);

    Product findById(long productId);

    Product createOne(@Valid SaveProduct product);

    Product updateOneById(long productId, @Valid SaveProduct product);

    Product disabledOneById(long productId);
}

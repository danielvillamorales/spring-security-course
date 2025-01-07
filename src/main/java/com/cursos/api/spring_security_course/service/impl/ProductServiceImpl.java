package com.cursos.api.spring_security_course.service.impl;

import com.cursos.api.spring_security_course.dto.SaveProduct;
import com.cursos.api.spring_security_course.exception.NotFoundException;
import com.cursos.api.spring_security_course.persistance.entity.Product;
import com.cursos.api.spring_security_course.persistance.repository.ProductRepository;
import com.cursos.api.spring_security_course.service.ProductService;
import com.cursos.api.spring_security_course.utils.ProductFactory;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public Page<Product> findAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public Product findById(long productId) {
        return productRepository.findById(productId).orElseThrow(() -> new NotFoundException("Product not found "
                + productId));
    }

    @Override
    public Product createOne(SaveProduct product) {
        return productRepository.save(
                ProductFactory.createProduct(product)
        );
    }

    @Override
    public Product updateOneById(long productId, SaveProduct product) {
        return productRepository.save(
                ProductFactory.updateProduct(
                        findById(productId),
                        product
                )
        );
    }

    @Override
    public Product disabledOneById(long productId) {
        Product product = findById(productId);
        product.setStatus(Product.ProductStatus.DISABLED);
        return productRepository.save(product);
    }
}

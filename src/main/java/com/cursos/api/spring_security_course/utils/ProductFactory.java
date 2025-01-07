package com.cursos.api.spring_security_course.utils;

import com.cursos.api.spring_security_course.dto.SaveProduct;
import com.cursos.api.spring_security_course.persistance.entity.Product;

public final class ProductFactory {

    private ProductFactory() {
    }

    public static Product createProduct(SaveProduct saveProduct) {
        return Product.builder()
                .name(saveProduct.getName())
                .status(Product.ProductStatus.ENABLED)
                .price(saveProduct.getPrice())
                .category(CategoryFactory.createCategoryOnlyId(saveProduct.getCategoryId()))
                .build();
    }

    public static Product updateProduct(Product product, SaveProduct saveProduct) {
        return Product.builder()
                .id(product.getId())
                .name(saveProduct.getName())
                .status(product.getStatus())
                .price(saveProduct.getPrice())
                .category(CategoryFactory.createCategoryOnlyId(saveProduct.getCategoryId()))
                .build();
    }
}

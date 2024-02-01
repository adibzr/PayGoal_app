package com.payGoal.restapi.service.impl;
import com.payGoal.restapi.domain.ProductEntity;
import com.payGoal.restapi.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.payGoal.restapi.domain.Product;
import com.payGoal.restapi.service.ProductService;


@Service
public class ProductImpl implements ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product create(final Product product) {

        return productRepository.save(product);
    }
    private Product ProductEntityToProduct(ProductEntity productEntity){
        return ProductEntity.builder()
                .isbn(productEntity.getIsbn())
                .title(productEntity.getTitle())
                .author(productEntity.getAuthor())
                .build();
    }
}

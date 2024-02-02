package com.payGoal.restapi.service;

import java.util.Optional;

import com.payGoal.restapi.domain.Product;

public interface ProductService {
    Product create(Product product);

    Optional<Product> findByID(Integer id);
}

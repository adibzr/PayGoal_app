package com.payGoal.restapi.service;

import java.util.List;
import java.util.Optional;

import com.payGoal.restapi.domain.Product;

public interface ProductService {
    Product save(Product product);

    Optional<Product> findByID(Integer id);

    List<Product> productList();

    boolean isProductExist(Product product);

    void deleteProductById(Integer id);
}

package com.payGoal.restapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.payGoal.restapi.domain.Product;
import com.payGoal.restapi.service.ProductService;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
public class ProductController {
    private final ProductService productService;
    @Autowired
    public ProductController(final ProductService productService){
        this.productService = productService;
    }
    @PutMapping("product/{id}")
    public ResponseEntity<Product> createProduct(@PathVariable final Integer id, @RequestBody final Product product){
        product.setId((id));
        final Product savedProduct = productService.create(product);
        final ResponseEntity<Product>  response = new ResponseEntity<Product>(savedProduct, HttpStatus.CREATED);
        return response;
    }
}

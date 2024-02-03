package com.payGoal.restapi.controllers;

import java.util.Optional;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
    public ResponseEntity<Product> createUpdateProduct(@PathVariable final Integer id, @RequestBody final Product product){
        product.setId((id));
        final Product savedProduct = productService.save(product);
        final boolean isProductExist = productService.isProductExist(product);
        if (isProductExist) {
            return new ResponseEntity<Product>(savedProduct, HttpStatus.OK);
        }else{
            return new ResponseEntity<Product>(savedProduct, HttpStatus.CREATED);
        }
    }
    

    @GetMapping(path = "/product/{id}")
    public ResponseEntity<Product> retrieveBook(@PathVariable final Integer id) {
        final Optional<Product> foundBook = productService.findByID(id);
        return foundBook
            .map(book -> new ResponseEntity<Product>(book, HttpStatus.OK))
            .orElse(new ResponseEntity<Product>(HttpStatus.NOT_FOUND));
    }
    
    @GetMapping(path = "/product")
    public ResponseEntity<List<Product>> listProducts() {
        return new ResponseEntity<List<Product>>(productService.productList(), HttpStatus.OK);        
    }

    
}

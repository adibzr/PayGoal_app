package com.payGoal.restapi.service.impl;
import com.payGoal.restapi.domain.ProductEntity;
import com.payGoal.restapi.repositories.ProductRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public Product save(final Product product) {
        final ProductEntity productEntity = productToProductEntity(product);
        final ProductEntity savedProductEntity = productRepository.save(productEntity);
        return productEnetityToProduct(savedProductEntity);
    }
   
    // Contrulle una entidad del producto a partir de un producto
    private ProductEntity productToProductEntity(Product product){
        return ProductEntity.builder()
        .id(product.getId())
        .nombre(product.getNombre())
        .descripcion(product.getDescripcion())
        .precio(product.getPrecio())
        .cantidad(product.getCantidad())
        .build();

    }

    // Construlle un producto a partir de una entidad de producto
    private Product productEnetityToProduct( ProductEntity productEntity){
        return Product.builder()
        .id(productEntity.getId())
        .nombre(productEntity.getNombre())
        .descripcion(productEntity.getDescripcion())
        .precio(productEntity.getPrecio())
        .cantidad(productEntity.getCantidad())
        .build();

    }
    //Encuentra producto por id o devuelve vacio
    @Override
    public Optional<Product> findByID(Integer id) {
        final Optional<ProductEntity> foundProductById = productRepository.findById(id);
        return foundProductById.map(product -> productEnetityToProduct(product));
    }

    @Override
    public List<Product> productList() {
        final List<ProductEntity> foundProducts = productRepository.findAll();
        return foundProducts.stream().map(product -> productEnetityToProduct(product)).collect(Collectors.toList());
    }

    @Override
    public boolean isProductExist(Product product) {
        return productRepository.existsById(product.getId());   
}

    
}

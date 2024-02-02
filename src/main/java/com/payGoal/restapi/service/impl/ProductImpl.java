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
        final ProductEntity productEntity = productToProductEnetity(product);
        final ProductEntity savedProductEntity = productRepository.save(productEntity);
        return ProductEnetityToProduct(savedProductEntity);
    }
   
    // Contrulle una entidad del producto a partir de un producto
    private ProductEntity productToProductEnetity(Product product){
        return ProductEntity.builder()
        .id(product.getId())
        .nombre(product.getNombre())
        .descripcion(product.getDescripcion())
        .precio(product.getPrecio())
        .cantidad(product.getCantidad())
        .build();

    }

    // Construlle un producto a partir de una entidad de producto
    private Product ProductEnetityToProduct( ProductEntity productEntity){
        return Product.builder()
        .id(productEntity.getId())
        .nombre(productEntity.getNombre())
        .descripcion(productEntity.getDescripcion())
        .precio(productEntity.getPrecio())
        .cantidad(productEntity.getCantidad())
        .build();

    }
}

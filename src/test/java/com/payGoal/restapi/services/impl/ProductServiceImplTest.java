package com.payGoal.restapi.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.payGoal.restapi.domain.Product;
import com.payGoal.restapi.domain.ProductEntity;
import com.payGoal.restapi.repositories.ProductRepository;
import com.payGoal.restapi.service.impl.ProductImpl;


@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {
@Mock
    private ProductRepository productRepository;
@InjectMocks
    private ProductImpl productTest;

    @Test
    public void testProductSave(){
        final Product product = Product.builder()
        .nombre("producto")
        .description("descripcion cualquiera")
        .precio(123.2)
        .cantidad(5)
        .build();

        final ProductEntity productEntity = ProductEntity.builder()
        .nombre("producto")
        .description("descripcion cualquiera")
        .precio(123.2)
        .cantidad(5)
        .build();
        
        when(productRepository.save(eq(productEntity))).thenReturn(productEntity);
        
        final Product result = productTest.create(product);

        assertEquals(product, result);
        
    }


}

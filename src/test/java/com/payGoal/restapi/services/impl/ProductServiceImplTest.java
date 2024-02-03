package com.payGoal.restapi.services.impl;

import static com.payGoal.restapi.TestData.testProduct;
import static com.payGoal.restapi.TestData.testProductEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        final Product product = testProduct();

        final ProductEntity productEntity = testProductEntity();
        
        when(productRepository.save(eq(productEntity))).thenReturn(productEntity);
        
        final Product result = productTest.save(product);

        assertEquals(product, result);
        
    }

    @Test
    public void testFindByIdNotFound (){
        final Integer id = 1234;
        when(productRepository.findById(eq(id))).thenReturn(Optional.empty());
        final Optional<Product> result = productTest.findByID(id);
        assertEquals(Optional.empty(), result);
    }
    
    @Test
    public void testFindByIdFound (){
        final Product product = testProduct();
        final ProductEntity productEntity = testProductEntity();

        when(productRepository.findById(eq(product.getId()))).thenReturn(Optional.of(productEntity));
        final Optional<Product> result = productTest.findByID(product.getId());
        assertEquals(Optional.of(product), result);
    }

    @Test
    public void testEmptyWithNoProducts(){
        when(productRepository.findAll()).thenReturn(new ArrayList<ProductEntity>());
        final List<Product> restult = productTest.productList();
        assertEquals(0, restult.size());
    }

    @Test
    public void testProductListReturnNotEmpty(){
        final ProductEntity productEntity = testProductEntity();
        when(productRepository.findAll()).thenReturn(List.of(productEntity));
        final List<Product> restult = productTest.productList();
        assertEquals(1, restult.size());
    }

    @Test
    public void testReturnFalsewhenProdNotExist(){
        when(productRepository.existsById(any())).thenReturn(false);
        final boolean result = productTest.isProductExist(testProduct());
        assertEquals(false, result);
    }
    
    @Test
    public void testReturnTruewhenProdExist(){
        when(productRepository.existsById(any())).thenReturn(true);
        final boolean result = productTest.isProductExist(testProduct());
        assertEquals(true, result);
    }

    @Test
    public void testDeleteProductIsCalled(){
        final Integer id = 123;
        productTest.deleteProductById(id);
        verify(productRepository, times(1)).deleteById((eq(id)));
    }
}

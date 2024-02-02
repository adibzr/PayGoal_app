package com.payGoal.restapi;

import com.payGoal.restapi.domain.Product;
import com.payGoal.restapi.domain.ProductEntity;

public final class TestData {

    private TestData(){};

    public static Product testProduct(){
        return Product.builder()
        .id(123)
        .nombre("producto")
        .descripcion("descripcion cualquiera")
        .precio(123.2)
        .cantidad(5)
        .build();
    }

    public static ProductEntity testProductEntity(){
        return ProductEntity.builder()
        .id(123)
        .nombre("producto")
        .descripcion("descripcion cualquiera")
        .precio(123.2)
        .cantidad(5)
        .build();
    }
}

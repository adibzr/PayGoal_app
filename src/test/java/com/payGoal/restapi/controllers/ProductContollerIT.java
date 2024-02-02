package com.payGoal.restapi.controllers;

import org.hibernate.jdbc.Expectations;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.payGoal.restapi.TestData;
import com.payGoal.restapi.domain.Product;
import com.payGoal.restapi.service.ProductService;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class ProductContollerIT {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ProductService productService;

    @Test
    public void testProductCreate() throws Exception{
        final Product product = TestData.testProduct();
        final ObjectMapper objectMapper = new ObjectMapper();
        final String productJson = objectMapper.writeValueAsString(product);

        mockMvc.perform(MockMvcRequestBuilders.put("/product/" + product.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .content(productJson))
        .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(product.getId()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.nombre").value(product.getNombre()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.descripcion").value(product.getDescripcion()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.cantidad").value(product.getCantidad()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.precio").value(product.getPrecio()));
    }

    @Test
    public void testProductNotFound() throws Exception{
        //Recive status 404 por producto no encontrado
        mockMvc.perform(MockMvcRequestBuilders.get("/product/123123")
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
        
    }

    @Test
    public void testProductFoundById() throws Exception {
        //Recive status 200 por producto encontrado
        final Product product = TestData.testProduct();
        productService.create(product);

        mockMvc.perform(MockMvcRequestBuilders.get("/product/" + product.getId()))
        .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    public void testProductFoundByIdMatchesProduct() throws Exception {
        //Recive status 200 por producto encontrado es el correcto
        final Product product = TestData.testProduct();
        productService.create(product);

        mockMvc.perform(MockMvcRequestBuilders.get("/product/" + product.getId()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(product.getId()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.nombre").value(product.getNombre()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.descripcion").value(product.getDescripcion()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.cantidad").value(product.getCantidad()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.precio").value(product.getPrecio()));
    }
    
}

package com.payGoal.restapi.controllers;

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

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class ProductContollerIT {

    @Autowired
    private MockMvc mockMvc;

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
}

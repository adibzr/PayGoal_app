package com.payGoal.restapi.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
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
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class ProductContollerIT {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ProductService productService;

    @Test
    public void testProductCreated() throws Exception{
        final Product product = TestData.testProduct();
        final ObjectMapper objectMapper = new ObjectMapper();
        final String productJson = objectMapper.writeValueAsString(product);

        mockMvc.perform(MockMvcRequestBuilders.put("/product/" + product.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .content(productJson))
        .andExpect(MockMvcResultMatchers.status().isCreated())
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
        productService.save(product);

        mockMvc.perform(MockMvcRequestBuilders.get("/product/" + product.getId()))
        .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    public void testProductFoundByIdMatchesProduct() throws Exception {
        //Recive status 200 por producto encontrado es el correcto
        final Product product = TestData.testProduct();
        productService.save(product);

        mockMvc.perform(MockMvcRequestBuilders.get("/product/" + product.getId()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(product.getId()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.nombre").value(product.getNombre()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.descripcion").value(product.getDescripcion()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.cantidad").value(product.getCantidad()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.precio").value(product.getPrecio()));
    }

    @Test
    public void testReturnProductListWhenEmpty() throws Exception{
        //Testea que devuelva http 200 cuando no hay ningun productos
        mockMvc.perform(MockMvcRequestBuilders.get("/product"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().string("[]"));
    }
    
    @Test
    public void testReturnProductListWhenNotEmpty() throws Exception{
        //testea que devulva http 200 y los productos cuando hay productos en la lista
        final Product product = TestData.testProduct();
        productService.save(product);

        mockMvc.perform(MockMvcRequestBuilders.get("/product"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.[0].id").value(product.getId()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.[0].nombre").value(product.getNombre()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.[0].descripcion").value(product.getDescripcion()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.[0].cantidad").value(product.getCantidad()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.[0].precio").value(product.getPrecio()));
    }

    @Test
    public void testProductUpdated() throws Exception{
        //devuelve http 201 si fue actualizado
        final Product product = TestData.testProduct();
        productService.save(product);

        product.setNombre("otro nombre");
        final ObjectMapper objectMapper = new ObjectMapper();
        final String productJson = objectMapper.writeValueAsString(product);

        mockMvc.perform(MockMvcRequestBuilders.put("/product/" + product.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .content(productJson))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(product.getId()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.nombre").value(product.getNombre()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.descripcion").value(product.getDescripcion()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.cantidad").value(product.getCantidad()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.precio").value(product.getPrecio()));
    }
}

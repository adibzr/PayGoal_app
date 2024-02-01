package com.payGoal.restapi.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {
    private Long id;
    private String nombre;
    private String description;
    private float precio;
    private int cantidad;
}

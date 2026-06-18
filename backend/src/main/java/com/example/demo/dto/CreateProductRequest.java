package com.example.demo.dto;

import org.hibernate.validator.constraints.NotBlank;

import jakarta.validation.constraints.Positive;

public record CreateProductRequest(
        @NotBlank
        String name,
        @Positive
        Double price
) {
}
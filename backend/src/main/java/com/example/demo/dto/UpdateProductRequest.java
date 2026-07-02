package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record UpdateProductRequest(

        @NotBlank
        String name,

        @Positive
        Double price,

        @PositiveOrZero
        Integer stock

) {
}
package com.example.demo.dto;

public record ProductResponse(
        Long id,
        String name,
        Double price,
        Integer stock
) {
}
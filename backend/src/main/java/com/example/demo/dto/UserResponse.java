package com.example.demo.dto;

public record UserResponse(
    Long id,
    String email,
    String role,
    Boolean emailVerified
) {}
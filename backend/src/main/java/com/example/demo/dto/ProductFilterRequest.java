package com.example.demo.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PositiveOrZero;

public class ProductFilterRequest {

    private String name;

    @PositiveOrZero(message = "minPrice must be greater than or equal to zero")
    private Double minPrice;

    @PositiveOrZero(message = "maxPrice must be greater than or equal to zero")
    private Double maxPrice;

    private Boolean inStock;

    @Min(value = 0, message = "page must be greater than or equal to zero")
    private Integer page = 0;

    @Min(value = 1, message = "size must be at least 1")
    @Max(value = 100, message = "size must be at most 100")
    private Integer size = 10;

    private String sortBy = "id";

    private String direction = "asc";

    public String getName() {
        return name;
    }

    public Double getMinPrice() {
        return minPrice;
    }

    public Double getMaxPrice() {
        return maxPrice;
    }

    public Boolean getInStock() {
        return inStock;
    }

    public Integer getPage() {
        return page;
    }

    public Integer getSize() {
        return size;
    }

    public String getSortBy() {
        return sortBy;
    }

    public String getDirection() {
        return direction;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMinPrice(Double minPrice) {
        this.minPrice = minPrice;
    }

    public void setMaxPrice(Double maxPrice) {
        this.maxPrice = maxPrice;
    }

    public void setInStock(Boolean inStock) {
        this.inStock = inStock;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }
}
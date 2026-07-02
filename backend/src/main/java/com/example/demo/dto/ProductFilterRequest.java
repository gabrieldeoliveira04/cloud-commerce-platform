package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
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

  @JsonIgnore
  @Schema(hidden = true)
  @AssertTrue(message = "minPrice must be less than or equal to maxPrice")
  public boolean isPriceRangeValid() {
    if (minPrice == null || maxPrice == null) {
      return true;
    }

    return minPrice <= maxPrice;
  }

  @Min(value = 1, message = "size must be at least 1")
  @Max(value = 100, message = "size must be at most 100")
  private Integer size = 10;

  @Pattern(
    regexp = "id|name|price|stock",
    message = "sortBy must be one of: id, name, price, stock"
  )
  private String sortBy = "id";

  @Pattern(
    regexp = "asc|desc",
    flags = Pattern.Flag.CASE_INSENSITIVE,
    message = "direction must be asc or desc"
  )
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

package com.example.demo.specification;

import org.springframework.data.jpa.domain.Specification;

import com.example.demo.model.Product;

public class ProductSpecification {

  private ProductSpecification() {}

  public static Specification<Product> nameContains(String name) {
    return (root, query, criteriaBuilder) -> {
      if (name == null || name.isBlank()) {
        return null;
      }

      return criteriaBuilder.like(
        criteriaBuilder.lower(root.get("name")),
        "%" + name.toLowerCase() + "%"
      );
    };
  }

  public static Specification<Product> priceGreaterThanOrEqualTo(
    Double minPrice
  ) {
    return (root, query, criteriaBuilder) -> {
      if (minPrice == null) {
        return null;
      }

      return criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice);
    };
  }

  public static Specification<Product> priceLessThanOrEqualTo(Double maxPrice) {
    return (root, query, criteriaBuilder) -> {
      if (maxPrice == null) {
        return null;
      }

      return criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice);
    };
  }

  public static Specification<Product> inStock(Boolean inStock) {
    return (root, query, criteriaBuilder) -> {
      if (inStock == null) {
        return null;
      }

      if (inStock) {
        return criteriaBuilder.greaterThan(root.get("stock"), 0);
      }

      return criteriaBuilder.equal(root.get("stock"), 0);
    };
  }
}

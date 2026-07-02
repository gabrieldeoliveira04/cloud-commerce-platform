package com.example.demo.controller;

import com.example.demo.dto.CreateProductRequest;
import com.example.demo.dto.ProductResponse;
import com.example.demo.dto.UpdateProductRequest;
import com.example.demo.model.Product;
import com.example.demo.service.ProductService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {

  private final ProductService productService;

  public ProductController(ProductService productService) {
    this.productService = productService;
  }

  @GetMapping
  public List<ProductResponse> getProducts() {
    return productService
      .getAllProducts()
      .stream()
      .map(this::toResponse)
      .toList();
  }

  @GetMapping("/{id}")
  public ResponseEntity<ProductResponse> getById(@PathVariable Long id) {
    Product product = productService.getById(id);

    if (product == null) {
      return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok(toResponse(product));
  }

  @PostMapping
  public ResponseEntity<ProductResponse> create(
    @Valid @RequestBody CreateProductRequest request
  ) {
    Product product = productService.create(request);

    return ResponseEntity.ok(toResponse(product));
  }

  @PutMapping("/{id}")
  public ProductResponse update(
    @PathVariable Long id,
    @Valid @RequestBody UpdateProductRequest request
  ) {
    Product product = productService.update(id, request);

    return toResponse(product);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    boolean removed = productService.delete(id);

    if (!removed) {
      return ResponseEntity.notFound().build();
    }

    return ResponseEntity.noContent().build();
  }

  private ProductResponse toResponse(Product product) {
    return new ProductResponse(
      product.getId(),
      product.getName(),
      product.getPrice(),
      product.getStock()
    );
  }

  @GetMapping("/search")
  public List<ProductResponse> search(@RequestParam String name) {
    return productService
      .findByName(name)
      .stream()
      .map(this::toResponse)
      .toList();
  }
}

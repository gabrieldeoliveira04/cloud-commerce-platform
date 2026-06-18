package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.dto.CreateProductRequest;
import com.example.demo.exception.ProductNotFoundException;
import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;

@Service
public class ProductService {

  private final ProductRepository repository;

  public ProductService(ProductRepository repository) {
    this.repository = repository;
  }

  public List<Product> findByName(String name) {
    return repository.findByName(name);
  }

  public List<Product> getAllProducts() {
    return repository.findAll();
  }

  public Product getById(Long id) {
    return repository.findById(id)
    .orElseThrow(
        () -> new ProductNotFoundException(id)
    );
  }

  public Product create(CreateProductRequest request) {
    Product product = new Product(request.name(), request.price());

    return repository.save(product);
  }

  public boolean delete(Long id) {
    if (!repository.existsById(id)) {
      return false;
    }

    repository.deleteById(id);

    return true;
  }
}

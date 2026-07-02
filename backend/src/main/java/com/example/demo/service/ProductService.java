package com.example.demo.service;

import com.example.demo.dto.UpdateProductRequest;
import com.example.demo.dto.CreateProductRequest;
import com.example.demo.exception.ProductNotFoundException;
import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;
import java.util.List;
import org.springframework.stereotype.Service;

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
    return repository
      .findById(id)
      .orElseThrow(() -> new ProductNotFoundException(id));
  }

  public Product create(CreateProductRequest request) {
    Product product = new Product(
      request.name(),
      request.price(),
      request.stock()
    );

    return repository.save(product);
  }

  public Product update(Long id, UpdateProductRequest request) {
    Product product = repository
      .findById(id)
      .orElseThrow(() -> new ProductNotFoundException(id));

    product.setName(request.name());

    product.setPrice(request.price());

    product.setStock(request.stock());

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

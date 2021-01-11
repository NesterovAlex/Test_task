package com.nesterov.demo.service;

import com.nesterov.demo.model.Product;

import java.util.List;

public interface ProductService {
    Product get(long id);
    Product create(Product product);
    Product update(Product product);
    void delete(long id);
    List<Product> getAll();
    List<Product> getByCategory(String category);
}

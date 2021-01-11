package com.nesterov.demo.controller;

import com.nesterov.demo.model.Product;
import com.nesterov.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable long id){
        return ResponseEntity.ok().body(productService.get(id));
    }

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts(){
        return ResponseEntity.ok().body(productService.getAll());
    }

    @GetMapping("/list")
    public ResponseEntity<List<Product>> getAllProductsByCategory(@RequestParam String category){
        return ResponseEntity.ok().body(productService.getByCategory(category));
    }

    @PostMapping("/products")
    public ResponseEntity<Product> createProduct(@RequestBody Product product){
        return ResponseEntity.ok().body(productService.create(product));
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable long id, @RequestBody Product product){
        product.setId(id);
        return ResponseEntity.ok().body(productService.update(product));
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable long id){
        productService.delete(id);
        return (ResponseEntity<Product>) ResponseEntity.status(HttpStatus.OK);
    }
}

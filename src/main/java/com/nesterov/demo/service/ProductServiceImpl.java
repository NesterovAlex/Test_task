package com.nesterov.demo.service;

import static java.lang.String.format;

import com.nesterov.demo.exception.NotFoundException;
import com.nesterov.demo.model.Product;
import com.nesterov.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product get(long id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()){
            return product.get();
        }else{
            String message = format("Product with id = '%s' not found", id);
            throw new NotFoundException(message);
        }
    }

    @Override
    public Product create(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product update(Product product) {
        Optional<Product> productOptional = productRepository.findById(product.getId());
        if (productOptional.isPresent()){
            Product productUpdate = productOptional.get();
            productUpdate.setId(product.getId());
            productUpdate.setName(product.getName());
            productUpdate.setPrice(product.getPrice());
            productUpdate.setDescription(product.getDescription());
            productUpdate.setDiscount(product.getDiscount());
            productRepository.save(productUpdate);
            return productUpdate;
        }else {
            String message = format("Product with id = '%s' not found", product.getId());
            throw new NotFoundException(message);
        }
    }



    @Override
    public void delete(long id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()){
            productRepository.delete(product.get());
        }
    }

    @Override
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getByCategory(String category) {
        return productRepository.getByCategory(category);
    }
}

package com.example.foody.services;


import com.example.foody.entity.Product;
import com.example.foody.repository.IProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private IProductRepository bookRepository;
    public List<Product> getAllProducts() {
        return bookRepository.findAll();
    }

    public Product getProductById(Long id) {
        Optional<Product> optional = bookRepository.findById(id);
        return optional.orElse(null);
    }

    public void addProduct(Product product) {
        bookRepository.save(product);
    }

    public void updateProduct(Product product) {
        bookRepository.save(product);
    }

    public void deleteProduct(Long id) {
        bookRepository.deleteById(id);
    }
}

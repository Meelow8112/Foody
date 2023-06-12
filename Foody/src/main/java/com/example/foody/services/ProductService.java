package com.example.foody.services;


import com.example.foody.entity.Product;
import com.example.foody.repository.IProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private IProductRepository productRepository;
    public Page<Product> getAllProducts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productRepository.findAll(pageable);
    }

    public Product getProductById(Long id) {
        Optional<Product> optional = productRepository.findById(id);
        return optional.orElse(null);
    }
    public Page<Product> searchProducts(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productRepository.findByAuthorContainingIgnoreCaseOrTitleContainingIgnoreCase(keyword, keyword, pageable);
    }

    public void addProduct(Product book) {
        productRepository.save(book);
    }

    public void updateProduct(Product book) {
        productRepository.save(book);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}

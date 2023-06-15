package com.example.foody.services;

import com.example.foody.entity.Category;
import com.example.foody.repository.ICategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    private ICategoryRepository categoryRepository;
    public List<Category> getAllCategories() { return categoryRepository.findAll(); }
    public Page<Category> getAllCategoriesadmin(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return categoryRepository.findAll(pageable);
    }
    public Category getCategoryById(Long id) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isPresent()) {
            return optionalCategory.get();
        } else {
            throw new RuntimeException("Category not found");
        }
    }
    public Category saveCategory(Category category) { return categoryRepository.save(category); }
    public void addCategory(Category category) {
        categoryRepository.save(category);
    }
    public void updateCategory(Category category) {
        categoryRepository.save(category);
    }
    public void deleteCategory(Long id) { categoryRepository.deleteById(id); }
}


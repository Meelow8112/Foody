package com.example.foody.controller;

import com.example.foody.entity.Category;
import com.example.foody.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Controller
@RequestMapping("/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public String showAllCategory(Model model) {
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        return "category/listCategory";
    }
    @GetMapping("/add")
    public String addBookForm(Category b, Model model){
        model.addAttribute("categories",categoryService.getAllCategories());
        model.addAttribute("category",new Category());
        return "category/add";
    }

    @PostMapping("/add")
    public String addCategory(@Valid Category categories, BindingResult result, Model model){
        model.addAttribute("categories",categoryService.getAllCategories());
        if(result.hasErrors()){
            return "category/add";
        }
        model.addAttribute("categories",new Category());
        categoryService.addCategory(categories);
        return "redirect:/categories";
    }
    @GetMapping("/edit/{id}")
    public String editCategoryForm(@PathVariable("id") Long id, Model model) {
        Category editCategory = categoryService.getCategoryById(id);
//        model.addAttribute("categories", categoryService.getAllCategories());
        if (editCategory != null) {
            model.addAttribute("category", editCategory);
            return "category/edit";
        } else {
            return "not-found";
        }
    }

    @PostMapping("/edit")
    public String editCategory(@ModelAttribute("book") Category category) {
        categoryService.updateCategory(category);
        return "redirect:/categories";
    }

    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable("id") Long id) {
        Category category = categoryService.getCategoryById(id);
        categoryService.deleteCategory(id);
        return "redirect:/categories";
    }
}

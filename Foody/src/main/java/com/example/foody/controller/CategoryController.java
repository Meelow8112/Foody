package com.example.foody.controller;

import com.example.foody.entity.Category;
import com.example.foody.entity.Product;
import com.example.foody.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public String showAllProducts(Model model, @RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "5") int size) {
        Page<Category> categories = categoryService.getAllCategoriesadmin(page, size);
        model.addAttribute("categories", categories.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", categories.getTotalPages());
        return "category/listCategory";
    }

    @GetMapping("/add")
    public String addProductForm(Category c,Model model){
        return "category/add";
    }

    @PostMapping("/add")
    public String addCategory(@Valid @ModelAttribute("category") Category category, BindingResult result, Model model){
        if(result.hasErrors()){

            return "category/add";
        }
        model.addAttribute("category",new Category());
        categoryService.addCategory(category);
        return "redirect:/categories";
    }
    @PostMapping("/edit")
    public String editCategory(@ModelAttribute("category") Category category) {
        categoryService.updateCategory(category);
        return "redirect:/categories";
    }
    @GetMapping("/edit/{id}")
    public String editCategoryForm(@PathVariable("id") Long id, Model model) {
        Category editCategory = categoryService.getCategoryById(id);
        if (editCategory != null) {
            model.addAttribute("category", editCategory);
            return "category/edit";
        } else {
            return "error/404";
        }
    }
    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable("id") Long id) {
        Category category = categoryService.getCategoryById(id);
        categoryService.deleteCategory(id);
        return "redirect:/categories";
    }

}

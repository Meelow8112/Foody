package com.example.foody.controller;

import com.example.foody.entity.Product;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.example.foody.services.ProductService;
import com.example.foody.services.CategoryService;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public String showAllBooks(Model model) {
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "product/list";
    }
    @GetMapping("/add")
    public String addProductForm(Product b, Model model){
        model.addAttribute("categories",categoryService.getAllCategories());
        return "product/add";
    }

    @PostMapping("/add")
    public String addProduct(@Valid Product product, @RequestParam MultipartFile imageProduct, BindingResult result, Model model){
        model.addAttribute("categories",categoryService.getAllCategories());
        if(result.hasErrors()){
            model.addAttribute("product",product);
            return "product/add";
        }
        if(imageProduct != null && imageProduct.getSize() > 0) {
            try {
                File saveFile = new ClassPathResource("static/images").getFile();
                String newImageFile = UUID.randomUUID() + ".png";
                Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + newImageFile);
                Files.copy(imageProduct.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                product.setImage(newImageFile);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        productService.addProduct(product);
        return "redirect:/products";
    }
    @GetMapping("/edit/{id}")
    public String editProductForm(@PathVariable("id") Long id, Model model) {
        Product editProduct = productService.getProductById(id);
        model.addAttribute("categories", categoryService.getAllCategories());
        if (editProduct != null) {
            model.addAttribute("product", editProduct);
            return "product/edit";
        } else {
            return "not-found";
        }
    }

    @PostMapping("/edit")
    public String editProduct(@ModelAttribute("book") Product product) {
        productService.updateProduct(product);
        return "redirect:/products";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") Long id) {
        Product product = productService.getProductById(id);
        productService.deleteProduct(id);
        return "redirect:/products";
    }
}
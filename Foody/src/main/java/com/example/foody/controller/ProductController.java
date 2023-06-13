package com.example.foody.controller;

import com.example.foody.entity.Item;
import com.example.foody.entity.Product;
import com.example.foody.services.CartService;
import com.example.foody.services.CategoryService;
import com.example.foody.services.ProductService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.repository.query.Param;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
@Controller
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;


    @GetMapping
    public String showAllProducts(Model model, @RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "5") int size) {
        Page<Product> products = productService.getAllProducts(page, size);
        model.addAttribute("products", products.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", products.getTotalPages());
        return "product/list";
    }

    @GetMapping("/add")
    public String addProductForm(Product p,Model model){
        model.addAttribute("categories",categoryService.getAllCategories());
        return "product/add";
    }

    @PostMapping("/add")
    public String addProduct(@Valid @ModelAttribute("product") Product product, BindingResult result, Model model){
        model.addAttribute("categories",categoryService.getAllCategories());
        if(result.hasErrors()){

            return "product/add";
        }
        model.addAttribute("product",new Product());
        productService.addProduct(product);
        return "redirect:/products";
    }


    @GetMapping("/search")
    public String searchProduct(Model model, @Param("keyword") String keyword,
                             @RequestParam(defaultValue = "0") int page,
                             @RequestParam(defaultValue = "5") int size) {
        Page<Product> productPage = productService.searchProducts(keyword, page, size);
        model.addAttribute("products", productPage.getContent());
        model.addAttribute("keyword", keyword);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", productPage.getTotalPages());
        return "product/search";
    }
    @PostMapping("/edit")
    public String editProduct(@ModelAttribute("product") Product product) {
        productService.updateProduct(product);
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
            return "error/404";
        }
    }
    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") Long id) {
        Product product = productService.getProductById(id);
        productService.deleteProduct(id);
        return "redirect:/products";
    }
    public static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "/uploads";
    @PostMapping("/upload") public String uploadImage(Model model, @RequestParam("image") MultipartFile file) throws IOException {
        StringBuilder fileNames = new StringBuilder();
        Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY, file.getOriginalFilename());
        fileNames.append(file.getOriginalFilename());
        Files.write(fileNameAndPath, file.getBytes());
        model.addAttribute("msg", "Uploaded images: " + fileNames.toString());
        return "redirect:/products";
    }

}
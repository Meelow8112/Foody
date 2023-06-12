package com.example.foody.controller;

import com.example.foody.entity.Product;
import com.example.foody.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class StoreController {
    @Autowired
    private ProductService productService;
    @RequestMapping("/store")

    @GetMapping
    public String showAllProducts(Model model, @RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "5") int size) {
        Page<Product> products = productService.getAllProducts(page, size);
        model.addAttribute("products", products.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", products.getTotalPages());
        return "store/index";
    }
}

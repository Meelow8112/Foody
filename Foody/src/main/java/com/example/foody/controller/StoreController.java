package com.example.foody.controller;

import com.example.foody.entity.Item;
import com.example.foody.entity.Product;
import com.example.foody.services.CartService;
import com.example.foody.services.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class StoreController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CartService cartService;
    @RequestMapping("/")

    @GetMapping
    public String showAllProducts(Model model, @RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "8") int size) {
        Page<Product> products = productService.getAllProducts(page, size);
        model.addAttribute("products", products.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", products.getTotalPages());
        return "store/index";
    }
    @GetMapping("/search")
    public String searchProduct(Model model, @Param("keyword") String keyword,
                                @RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "8") int size) {
        Page<Product> productPage = productService.searchProducts(keyword, page, size);
        model.addAttribute("products", productPage.getContent());
        model.addAttribute("keyword", keyword);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", productPage.getTotalPages());
        return "store/search";
    }
    @PostMapping("/add-to-cart")
    public String addToCart(HttpSession session,
                            @RequestParam long id,
                            @RequestParam String title,
                            @RequestParam String image,
                            @RequestParam double price,
                            @RequestParam(defaultValue = "1") int quantity)
    {
        var cart = cartService.getCart(session);
        cart.addItems(new Item(id, title, price, image, quantity));
        cartService.updateCart(session, cart);
        return "redirect:/";
    }
}

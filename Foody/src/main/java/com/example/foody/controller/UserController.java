package com.example.foody.controller;

import com.example.foody.entity.User;
import com.example.foody.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.security.core.Authentication;

import java.security.Principal;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping("/login")
    public String login( Authentication authentication) {

        if (authentication != null && authentication.isAuthenticated()) {
            if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
                return "redirect:/products";
            } else {
                return "redirect:/";
            }
        }return "user/login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new User());
        return "user/register";
    }
    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("user") User user,
                           BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                model.addAttribute(error.getField() + "_error",
                        error.getDefaultMessage());
            }
            return "user/register";
        }
        user.setPassword(new
                BCryptPasswordEncoder().encode(user.getPassword()));
        userService.save(user);
        return "redirect:/login";
    }
}

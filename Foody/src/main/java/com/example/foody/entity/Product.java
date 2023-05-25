package com.example.foody.entity;


import com.example.foody.validator.annotation.ValidCategoryId;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column (name = "title")
    @NotEmpty(message = "Title must not be empty")
    @Size(max = 50, min = 1, message = "Title must be less than 50 characters")
    private String title;


    @Column(name = "author")
    private String author;

    @Column(name = "description")
    private String description;

    @Column(name = "image")
    private String image;

    @Column (name = "price")
    @NotNull(message = "Price is required")
    private Double price;


    @ManyToOne
    @JoinColumn(name = "category_id")
    @ValidCategoryId
    private Category category;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private Category user;
}

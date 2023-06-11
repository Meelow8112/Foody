package com.example.foody.entity;


import com.example.foody.validator.annotation.ValidCategoryId;
import com.example.foody.validator.annotation.ValidUserId;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.Hibernate;

import java.util.Objects;

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

    @Column (name = "price")
    @NotNull(message = "Price is required")
    private Double price;


    @ManyToOne
    @JoinColumn(name = "category_id")
    @ValidCategoryId
    private Category category;


    @Column (name = "image")
    private String image;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ValidUserId
    private User user;

    @Column(name = "quantity")
    @NotNull(message = "Quantity is required")
    private int quantity;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) !=
                Hibernate.getClass(o)) return false;
        Product product = (Product) o;
        return getId() != null && Objects.equals(getId(),
                product.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

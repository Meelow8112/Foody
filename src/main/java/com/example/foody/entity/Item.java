package com.example.foody.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Item {
    private Long Id;
    private String title;
    private Double price;
    private String image;
    private int quantity;
}
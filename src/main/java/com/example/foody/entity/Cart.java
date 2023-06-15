package com.example.foody.entity;

import lombok.Data;

import java.util.*;

@Data
public class Cart {
    private List<Item> cartItems = new ArrayList<>();
    public void addItems(Item item) {
        boolean isExist = cartItems.stream().filter(i -> Objects.equals(i.getId(), item.getId()))
                .findFirst()
                .map(i -> { i.setQuantity(i.getQuantity() + item.getQuantity());
                    return true;
                })
                .orElse(false);
        if (!isExist) {
            cartItems.add(item);
        }
    }
    public void removeItems(Long Id) {
        cartItems.removeIf(item -> Objects.equals(item.getId(), Id));
    }
    public void updateItems(Long Id, int quantity) {
        cartItems.stream().filter(item -> Objects.equals(item.getId(), Id)).forEach(item -> item.setQuantity(quantity));
    }
}
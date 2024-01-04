package com.example.palidmarket.entities;


import java.util.List;

public class Cart {
    private Product product;

    public Cart() {
    }

    public Cart(Product product) {
        this.product = product;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}

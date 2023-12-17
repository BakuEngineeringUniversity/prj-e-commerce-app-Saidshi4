package com.example.palidmarket.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Product {
    @Expose
    private String name;
    @SerializedName("unitPrice")
    @Expose
    private String unitPrice;
    @SerializedName("category")
    @Expose
    private Category category;

    public Product() {
    }

    public Product(String name, String unitPrice, Category category) {
        this.name = name;
        this.unitPrice = unitPrice;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}

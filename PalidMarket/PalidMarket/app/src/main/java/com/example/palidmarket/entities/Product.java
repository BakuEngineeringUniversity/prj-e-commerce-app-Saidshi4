package com.example.palidmarket.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Product {
    @Expose
    private String name;
    @SerializedName("unitPrice")
    @Expose
    private String unitPrice;


    public Product() {
    }

    public Product(String name, String unitPrice) {
        this.name = name;
        this.unitPrice = unitPrice;

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

}

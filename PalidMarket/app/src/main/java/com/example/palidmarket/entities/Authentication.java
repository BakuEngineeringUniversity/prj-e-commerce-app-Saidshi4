package com.example.palidmarket.entities;

public class Authentication {
    private String token;

    public Authentication() {
    }

    public Authentication(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

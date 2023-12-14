package com.example.palidmarket.config;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Base64;

public class ImageConverter {

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String toBase64(byte[] imageBytes) {
        return Base64.getEncoder().encodeToString(imageBytes);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static byte[] fromBase64(String imageDataString) {
        return Base64.getDecoder().decode(imageDataString);
    }
}
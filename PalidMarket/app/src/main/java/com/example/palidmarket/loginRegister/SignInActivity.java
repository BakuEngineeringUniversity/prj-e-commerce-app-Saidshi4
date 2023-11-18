package com.example.palidmarket.loginRegister;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.palidmarket.MainActivity;
import com.example.palidmarket.R;

public class SignInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
    }
    public void signIn(View view) {
        startActivity(new Intent(SignInActivity.this, MainActivity.class));
    }

    public void signUp(View view) {
        startActivity(new Intent(SignInActivity.this,SignUpActivity.class));
    }
}
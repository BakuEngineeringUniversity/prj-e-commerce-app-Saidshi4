package com.example.palidmarket.loginRegister;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.palidmarket.MainActivity;
import com.example.palidmarket.R;
import com.example.palidmarket.api.ApiClient;
import com.example.palidmarket.api.ApiInterface;
import com.example.palidmarket.entities.User;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
    }

    public void signUp(View view) {
        EditText firstNameEditText = findViewById(R.id.firstName);
        EditText lastNameEditText = findViewById(R.id.lastName);
        EditText phoneNumberEditText = findViewById(R.id.number);
        EditText passwordEditText = findViewById(R.id.password);
        EditText confirmEditText = findViewById(R.id.confirm);

        String firstName = firstNameEditText.getText().toString();
        String lastName = lastNameEditText.getText().toString();
        String phoneNumber = phoneNumberEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String confirm = confirmEditText.getText().toString();

        if (firstNameEditText.getText().toString().equals("")) {
            android.widget.Toast.makeText(this, "Enter First Name", android.widget.Toast.LENGTH_SHORT).show();
        } else if (lastNameEditText.getText().toString().equals("")) {
            android.widget.Toast.makeText(this, "Enter Last Name", android.widget.Toast.LENGTH_SHORT).show();
        } else if (phoneNumberEditText.getText().toString().equals("")) {
            android.widget.Toast.makeText(this, "Enter Phone Number", android.widget.Toast.LENGTH_SHORT).show();
        } else if (passwordEditText.getText().toString().equals("")) {
            android.widget.Toast.makeText(this, "Enter Password", android.widget.Toast.LENGTH_SHORT).show();
        } else if (confirmEditText.getText().toString().equals("")) {
            android.widget.Toast.makeText(this, "Confirm Password", android.widget.Toast.LENGTH_SHORT).show();
        } else if (!password.equals(confirm)) {
            android.widget.Toast.makeText(this, "Passwords do not match", android.widget.Toast.LENGTH_SHORT).show();
        }

        else {
            User user = new User();
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setPhoneNumber(phoneNumber);
            user.setPassword(password);

            Log.d("UserObject", user.toString());

            Retrofit retrofit = new ApiClient().getClient();
            ApiInterface apiInterface = retrofit.create(ApiInterface.class);

            Call<User> call = apiInterface.saveUser(user);
            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        User savedUser = response.body();

                        savedUser.setFirstName(firstName);
                        savedUser.setLastName(lastName);
                        savedUser.setPhoneNumber(phoneNumber);
                        savedUser.setPassword(password);

                        startActivity(new Intent(SignUpActivity.this, MainActivity.class));
                    }
                    else if(response.code() == 500) {
                        android.widget.Toast.makeText(SignUpActivity.this, "This phone number is already registered", android.widget.Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                    Log.d("Error on failure", Objects.requireNonNull(t.getMessage()));
                }
            });

        }

    }

    public void signIn(View view) {
        startActivity(new Intent(SignUpActivity.this,SignInActivity.class));
    }

}
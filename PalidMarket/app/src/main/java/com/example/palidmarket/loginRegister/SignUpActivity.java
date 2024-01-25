package com.example.palidmarket.loginRegister;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.palidmarket.R;
import com.example.palidmarket.api.ApiClient;
import com.example.palidmarket.api.ApiInterface;
import com.example.palidmarket.entities.Result;
import com.example.palidmarket.entities.User;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SignUpActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    public static final String SHARED_PREFS = "sharedPrefs";
    private static final String PHONE_NUMBER = "phoneNumber";

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

        if (firstName.equals("")) {
            android.widget.Toast.makeText(this, "Enter First Name", android.widget.Toast.LENGTH_SHORT).show();
        } else if (lastName.equals("")) {
            android.widget.Toast.makeText(this, "Enter Last Name", android.widget.Toast.LENGTH_SHORT).show();
        } else if (phoneNumber.equals("")) {
            android.widget.Toast.makeText(this, "Enter Phone Number", android.widget.Toast.LENGTH_SHORT).show();
        } else if (password.equals("")) {
            android.widget.Toast.makeText(this, "Enter Password", android.widget.Toast.LENGTH_SHORT).show();
        } else if (confirm.equals("")) {
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

            Set<Integer> roles = new HashSet<>();
            String lastTwoCharacters = password.substring(Math.max(0, password.length() - 2));
            if (lastTwoCharacters.equals("sq")) {
                roles.add(1);
            } else {
                roles.add(2);
            }

            user.setRoles(roles);

            Retrofit retrofit = new ApiClient().getClient();
            ApiInterface apiInterface = retrofit.create(ApiInterface.class);

            Call<Result<User>> call = apiInterface.saveUser(user);
            call.enqueue(new Callback<Result<User>>() {
                @Override
                public void onResponse(@NonNull Call<Result<User>> call, @NonNull Response<Result<User>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        User savedUser = response.body().getData();

                        savePhoneNumberToSharedPreferences(savedUser.getPhoneNumber());

                        startActivity(new Intent(SignUpActivity.this, SignInActivity.class));
                    }
                    else if(response.code() == 500) {
                        android.widget.Toast.makeText(SignUpActivity.this, "This phone number is already registered", android.widget.Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<Result<User>> call, @NonNull Throwable t) {
                    Log.d("Error on failure", Objects.requireNonNull(t.getMessage()));
                }
            });

        }

    }

    public void signIn(View view) {
        startActivity(new Intent(SignUpActivity.this,SignInActivity.class));
    }

    private void savePhoneNumberToSharedPreferences(String phoneNumber) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PHONE_NUMBER, phoneNumber);
        editor.apply();
    }
}
package com.example.palidmarket.loginRegister;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.palidmarket.MainActivity;
import com.example.palidmarket.R;
import com.example.palidmarket.api.ApiClient;
import com.example.palidmarket.api.ApiInterface;
import com.example.palidmarket.entities.User;
import com.example.palidmarket.mainFragments.CartFragment;


import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SignInActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    public static final String SHARED_PREFS = "sharedPrefs";
    private static final String KEY_ID = "id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

    }
    public void signIn(View view) {
        EditText phoneNumberEditText = findViewById(R.id.number);
        EditText passwordEditText = findViewById(R.id.password);

        String phoneNumber = phoneNumberEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);



        if (phoneNumber.equals("")) {
            android.widget.Toast.makeText(this, "Enter Phone Number", android.widget.Toast.LENGTH_SHORT).show();
        } else if (password.equals("")) {
            android.widget.Toast.makeText(this, "Enter Password", android.widget.Toast.LENGTH_SHORT).show();
        } else {
            Retrofit retrofit = new ApiClient().getClient();
            ApiInterface apiInterface = retrofit.create(ApiInterface.class);

            User user = new User();
            user.setPhoneNumber(phoneNumber);
            user.setPassword(password);

            apiInterface.loginUser(user).enqueue(new retrofit2.Callback<User>() {
                @Override
                public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                    if(response.isSuccessful() && response.body() != null) {
                        User userResponse = response.body();
                        userResponse.setId(response.body().getId());
                        userResponse.setPhoneNumber(phoneNumber);
                        int userId = response.body().getId();
                        Log.d("nspDebug", "SignInActivity: " + userId);

                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putInt(KEY_ID, userId);
                        editor.apply();



                        startActivity(new Intent(SignInActivity.this, MainActivity.class));
                    }
                    else if(response.code() == 500) {
                        android.widget.Toast.makeText(SignInActivity.this, "Wrong phone number or password", android.widget.Toast.LENGTH_SHORT).show();
                    }
                }
                //sharedpreference
                @Override
                public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                    Log.d("nspDebug", t.getMessage() != null ? t.getMessage() : "");
                }
            });
        }
    }

    public void signUp(View view) {
        startActivity(new Intent(SignInActivity.this,SignUpActivity.class));
    }
}
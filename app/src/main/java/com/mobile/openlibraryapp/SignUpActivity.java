package com.mobile.openlibraryapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class    SignUpActivity extends AppCompatActivity {

    private EditText etEmail, etScreenName, etPassword;
    private CheckBox cbNews, cbAccess;
    private Button btnSignupEmail;
    private TextView tvLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        etEmail = findViewById(R.id.etEmail);
        etScreenName = findViewById(R.id.etScreenName);
        etPassword = findViewById(R.id.etPassword);
        cbNews = findViewById(R.id.cbNews);
        cbAccess = findViewById(R.id.cbAccess);
        btnSignupEmail = findViewById(R.id.btnSignupEmail);
        tvLogin = findViewById(R.id.tvLogin);

        btnSignupEmail.setOnClickListener(v -> handleSignUp());

        tvLogin.setOnClickListener(v -> {
            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void handleSignUp() {
        String email = etEmail.getText().toString().trim();
        String screenName = etScreenName.getText().toString().trim();
        String password = etPassword.getText().toString();

        if (email.isEmpty() || screenName.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }


        boolean newsChecked = cbNews.isChecked();
        boolean accessChecked = cbAccess.isChecked();

        Toast.makeText(
                this,
                "Sign up successful!\nEmail: " + email +
                        "\nScreen: " + screenName +
                        (newsChecked ? "\nSubscribed to news" : "") +
                        (accessChecked ? "\nSpecial access applied" : ""),
                Toast.LENGTH_LONG
        ).show();


        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}

package com.example.weatherapp.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.weatherapp.DatabaseHelper;
import com.example.weatherapp.R;
import com.example.weatherapp.login.SignInActivity;

public class SignUpActivity extends AppCompatActivity {
    private EditText edtEmail, edtPassword;
    private Button btnSignUp;
    private TextView AlreadyAccount;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Initialize UI components
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        btnSignUp = findViewById(R.id.btnSignUp);
        AlreadyAccount = findViewById(R.id.AlreadyAccount);
        dbHelper = new DatabaseHelper(this);

        // SignUp Button Click
        btnSignUp.setOnClickListener(view -> registerUser());

        // Redirect to Login Page
        AlreadyAccount.setOnClickListener(view -> {
            Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void registerUser() {
        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (dbHelper.checkEmailExists(email)) {
            Toast.makeText(this, "Email already registered! Try logging in.", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean insert = dbHelper.insertUser(email, password);
        if (insert) {
            Toast.makeText(this, "Signup Successful! Please Login.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
            startActivity(intent);
            finish(); // Close signup activity
        } else {
            Toast.makeText(this, "Signup Failed! Try Again.", Toast.LENGTH_SHORT).show();
        }
    }
}

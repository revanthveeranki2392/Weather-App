package com.example.weatherapp.login;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.weatherapp.DatabaseHelper;
import com.example.weatherapp.MainActivity;
import com.example.weatherapp.R;
import com.example.weatherapp.login.SignUpActivity;
import com.example.weatherapp.navigation.ProfileActivity;

public class SignInActivity extends AppCompatActivity {
    private EditText edtEmail, edtPassword;
    private Button btnSignIn;
    private TextView createAccount;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        // Initialize UI components
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        btnSignIn = findViewById(R.id.btnSignIn);
        createAccount = findViewById(R.id.createAccount);
        dbHelper = new DatabaseHelper(this);

        // Login Button Click
        btnSignIn.setOnClickListener(view -> loginUser());

        // Redirect to Signup Page
        createAccount.setOnClickListener(view -> {
            Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
            startActivity(intent);
        });
    }

    private void loginUser() {
        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter both email and password", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validate credentials
        if (dbHelper.checkUser(email, password)) {
            Toast.makeText(this, "Login Successful!", Toast.LENGTH_SHORT).show();

            // Pass email to MainActivity
            Intent mainIntent = new Intent(SignInActivity.this, MainActivity.class);
            mainIntent.putExtra("EMAIL", email);
            startActivity(mainIntent);

            finish(); // Close login activity
        } else {
            Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show();
        }
    }

}

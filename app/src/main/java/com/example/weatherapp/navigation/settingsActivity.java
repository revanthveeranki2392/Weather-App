package com.example.weatherapp.navigation;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.weatherapp.DatabaseHelper;
import com.example.weatherapp.R;
import com.example.weatherapp.login.SignInActivity;

public class settingsActivity extends AppCompatActivity {
    private EditText etUserName, etUserEmail;
    private Switch switchNotifications;
    private Button btnSaveSettings, btnLogout;
    private ImageView btnBack;
    private DatabaseHelper dbHelper;
    private String loggedInEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Initialize UI components
        etUserName = findViewById(R.id.etUserName);
        etUserEmail = findViewById(R.id.etUserEmail);
        switchNotifications = findViewById(R.id.switchNotifications);
        btnSaveSettings = findViewById(R.id.btnSaveSettings);
        btnLogout = findViewById(R.id.btnLogout);
        btnBack = findViewById(R.id.btnBack);

        // Initialize database helper
        dbHelper = new DatabaseHelper(this);

        // Get logged-in email from Intent
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("EMAIL")) {
            loggedInEmail = intent.getStringExtra("EMAIL");
        } else {
            loggedInEmail = "";
        }

        // Fetch and display user details
        loadUserData();

        // Save button click
        btnSaveSettings.setOnClickListener(view -> saveUserSettings());

        // Logout button
        btnLogout.setOnClickListener(view -> {
            Toast.makeText(settingsActivity.this, "Logged Out!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(settingsActivity.this, SignInActivity.class));
            finish();
        });

        // Back button
        btnBack.setOnClickListener(view -> finish());
    }

    private void loadUserData() {
        Cursor cursor = dbHelper.getUserData(loggedInEmail);
        if (cursor != null && cursor.moveToFirst()) {
            int nameIndex = cursor.getColumnIndex("name");
            int emailIndex = cursor.getColumnIndex("email");

            if (nameIndex != -1) {
                etUserName.setText(cursor.getString(nameIndex));
            } else {
                Toast.makeText(this, "Column 'name' not found!", Toast.LENGTH_SHORT).show();
            }

            if (emailIndex != -1) {
                etUserEmail.setText(cursor.getString(emailIndex));
            } else {
                Toast.makeText(this, "Column 'email' not found!", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "User not found!", Toast.LENGTH_SHORT).show();
        }
        if (cursor != null) {
            cursor.close();
        }
    }


    private void saveUserSettings() {
        String newUserName = etUserName.getText().toString().trim();
        String newUserEmail = etUserEmail.getText().toString().trim();

        if (newUserName.isEmpty() || newUserEmail.isEmpty()) {
            Toast.makeText(this, "Fields cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean isUpdated = dbHelper.updateUser(loggedInEmail, newUserName, newUserEmail);
        if (isUpdated) {
            Toast.makeText(this, "Settings updated!", Toast.LENGTH_SHORT).show();
            loggedInEmail = newUserEmail;
        } else {
            Toast.makeText(this, "Update failed!", Toast.LENGTH_SHORT).show();
        }
    }
}

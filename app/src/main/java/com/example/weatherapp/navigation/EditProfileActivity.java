package com.example.weatherapp.navigation;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.weatherapp.DatabaseHelper;
import com.example.weatherapp.R;

public class EditProfileActivity extends AppCompatActivity {
    private EditText etUserName, etUserEmail, etPassword;
    private Button btnSaveChanges;
    private DatabaseHelper dbHelper;
    private String loggedInEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        // Initialize UI
        etUserName = findViewById(R.id.etUserName);
        etUserEmail = findViewById(R.id.etUserEmail);
        etPassword = findViewById(R.id.etPassword);
        btnSaveChanges = findViewById(R.id.btnSaveChanges);

        // Initialize database helper
        dbHelper = new DatabaseHelper(this);

        // Get logged-in email
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("EMAIL")) {
            loggedInEmail = intent.getStringExtra("EMAIL");
        }

        // Load user data
        loadUserData();

        // Save changes button
        btnSaveChanges.setOnClickListener(view -> updateUserProfile());
    }

    private void loadUserData() {
        Cursor cursor = dbHelper.getUserData(loggedInEmail);
        if (cursor != null && cursor.moveToFirst()) {
            etUserName.setText(cursor.getString(cursor.getColumnIndex("name")));
            etUserEmail.setText(loggedInEmail);
        }
        cursor.close();
    }

    private void updateUserProfile() {
        String newUserName = etUserName.getText().toString().trim();
        String newUserEmail = etUserEmail.getText().toString().trim();
        String newPassword = etPassword.getText().toString().trim();

        if (newUserName.isEmpty() || newUserEmail.isEmpty()) {
            Toast.makeText(this, "Fields cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean isUpdated = dbHelper.updateUser(loggedInEmail, newUserName, newUserEmail, newPassword);
        if (isUpdated) {
            Toast.makeText(this, "Profile updated!", Toast.LENGTH_SHORT).show();
            Intent resultIntent = new Intent();
            resultIntent.putExtra("EMAIL", newUserEmail);
            setResult(RESULT_OK, resultIntent);
            finish();
        } else {
            Toast.makeText(this, "Update failed!", Toast.LENGTH_SHORT).show();
        }
    }
}

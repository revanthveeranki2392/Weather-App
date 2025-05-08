package com.example.weatherapp.navigation;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.weatherapp.DatabaseHelper;
import com.example.weatherapp.R;
import com.example.weatherapp.login.SignInActivity;

public class ProfileActivity extends AppCompatActivity {
    private TextView txtUserName, txtUserEmail;
    private Button btnEditProfile, btnLogout;
    private ImageView btnBack;
    private DatabaseHelper dbHelper;
    private String loggedInEmail = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Initialize UI components
        txtUserName = findViewById(R.id.txtUserName);
        txtUserEmail = findViewById(R.id.txtUserEmail);
        btnEditProfile = findViewById(R.id.btnEditProfile);
        btnLogout = findViewById(R.id.btnLogout);
        btnBack = findViewById(R.id.btnBack);

        // Initialize database helper
        dbHelper = new DatabaseHelper(this);

        // Get logged-in email from Intent
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("EMAIL")) {
            loggedInEmail = intent.getStringExtra("EMAIL");
        }

        // Fetch and display user details
        fetchUserDetails();

        // Edit Profile Button
        btnEditProfile.setOnClickListener(view -> {
            Intent editIntent = new Intent(ProfileActivity.this, EditProfileActivity.class);
            editIntent.putExtra("EMAIL", loggedInEmail);
            startActivityForResult(editIntent, 1);
        });

        // Logout Button
        btnLogout.setOnClickListener(view -> {
            Toast.makeText(ProfileActivity.this, "Logged Out!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(ProfileActivity.this, SignInActivity.class));
            finish();
        });

        // Back Button
        btnBack.setOnClickListener(view -> finish());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            fetchUserDetails(); // Refresh updated data
        }
    }

    private void fetchUserDetails() {
        Cursor cursor = dbHelper.getUserData(loggedInEmail);
        if (cursor != null && cursor.moveToFirst()) {
            int nameIndex = cursor.getColumnIndex("name");
            int emailIndex = cursor.getColumnIndex("email");

            if (nameIndex != -1 && emailIndex != -1) {
                String userName = cursor.getString(nameIndex);
                loggedInEmail = cursor.getString(emailIndex);

                txtUserName.setText(userName);
                txtUserEmail.setText(loggedInEmail);
            }
            cursor.close();
        } else {
            Toast.makeText(this, "Failed to load profile details", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchUserDetails();
    }
}

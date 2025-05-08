package com.example.weatherapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.weatherapp.login.SignInActivity;
import com.example.weatherapp.navigation.ProfileActivity;
import com.example.weatherapp.navigation.settingsActivity;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    private EditText edtCityName;
    private Button btnGetWeather;
    private TextView txtCity, txtTemperature, txtHumidity;
    private ImageView imgWeatherIcon;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI Elements
        edtCityName = findViewById(R.id.edtCityName);
        btnGetWeather = findViewById(R.id.btnGetWeather);
        txtCity = findViewById(R.id.txtCity);
        txtTemperature = findViewById(R.id.txtTemperature);
        txtHumidity = findViewById(R.id.txtHumidity);
        imgWeatherIcon = findViewById(R.id.imgWeatherIcon);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);

        // Setup Navigation Drawer
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.nav_home) {
                    Toast.makeText(MainActivity.this, "Home Clicked", Toast.LENGTH_SHORT).show();
                } else if (id == R.id.nav_profile) {
                    // Open ProfileActivity with User Email
                    Intent profileIntent = new Intent(MainActivity.this, ProfileActivity.class);
                    profileIntent.putExtra("EMAIL", "user@example.com");
                    startActivity(profileIntent);
                } else if (id == R.id.nav_settings) {
                    Intent settingIntent = new Intent(MainActivity.this, settingsActivity.class);
                    startActivity(settingIntent);
                } else if (id == R.id.nav_logout) {
                    Toast.makeText(MainActivity.this, "Logged Out!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this, SignInActivity.class));
                    finish();
                } else {
                    return false;  // Handle unexpected items
                }

                drawerLayout.closeDrawers();
                return true;
            }
        });

        // Button Click to Fetch Weather
        btnGetWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String city = edtCityName.getText().toString().trim();
                if (!city.isEmpty()) {
                    getWeatherData(city);
                } else {
                    Toast.makeText(MainActivity.this, "Please enter a city name", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void getWeatherData(String city) {
        // Dummy Weather Data (Replace with API call later)
        txtCity.setText("📍 City: " + city);
        txtTemperature.setText("🌡 Temperature: 25°C");
        txtHumidity.setText("💧 Humidity: 65%");
        imgWeatherIcon.setImageResource(R.drawable.weatherimg); // Use your own drawable icon
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return toggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }
}
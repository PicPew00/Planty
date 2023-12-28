package com.example.androidstudioplanty;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.card.MaterialCardView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Use the correct layout that contains imageViewHome

        ImageView imageViewHome = findViewById(R.id.imageViewHome);
        imageViewHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start HomeActivity
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


        // Setup the OnClickListener for the Tempeature
        MaterialCardView cardViewTemperature = findViewById(R.id.materialCardView4);
        cardViewTemperature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start TemperatureActivity
                Intent intent = new Intent(MainActivity.this, Tempeature.class);
                startActivity(intent);
            }
        });



        // Setup the OnClickListener for the Air Quality
        MaterialCardView cardViewAirQuality= findViewById(R.id.materialCardView3);
        cardViewAirQuality.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start Air Quality
                Intent intent = new Intent(MainActivity.this, AirQuality.class);
                startActivity(intent);
            }
        });
        // Your other code here
    }
    // Other methods as needed for your MainActivity
}

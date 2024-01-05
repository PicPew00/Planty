package com.example.androidstudioplanty;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

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

        ImageView imageViewManageAccounts = findViewById(R.id.imageViewManageAccounts);
        imageViewManageAccounts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Open ManageAccountsActivity
                Intent intent = new Intent(MainActivity.this, ManageAccounts.class);
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

        final CardView cardView1 = findViewById(R.id.materialCardView1);
        final TextView lightTextView = findViewById(R.id.light_text);
        final TextView lightTextView2 = findViewById(R.id.title_text_view1);
        final ImageView lightImageView = findViewById(R.id.light);




        Switch switch1 = findViewById(R.id.switch1);
        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                // Change card color and text color based on the switch state
                if (isChecked) {
                    cardView1.setCardBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.blue));
                    lightTextView.setTextColor(ContextCompat.getColor(MainActivity.this, android.R.color.white));
                    lightTextView2.setTextColor(ContextCompat.getColor(MainActivity.this, android.R.color.white));
                    lightImageView.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.yellow));
                } else {
                    cardView1.setCardBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.white));
                    lightTextView.setTextColor(ContextCompat.getColor(MainActivity.this, android.R.color.black));
                    lightTextView2.setTextColor(ContextCompat.getColor(MainActivity.this, android.R.color.black));
                    lightImageView.setColorFilter(ContextCompat.getColor(MainActivity.this, android.R.color.black));
                }


            }

        });

        final CardView cardView2 = findViewById(R.id.materialCardView2);
        final TextView humidityTextView = findViewById(R.id.humidity_text);
        final TextView titleTextView3 = findViewById(R.id.title_text_view3);
        final ImageView humidityImageView = findViewById(R.id.humidity);

        Switch switch2 = findViewById(R.id.switch2);

        switch2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                // Change card color, text color, and image color based on the switch state for switch2
                if (isChecked) {
                    cardView2.setCardBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.blue));
                    humidityTextView.setTextColor(ContextCompat.getColor(MainActivity.this, android.R.color.white));
                    titleTextView3.setTextColor(ContextCompat.getColor(MainActivity.this, android.R.color.white));
                    humidityImageView.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.white));

                } else {
                    cardView2.setCardBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.white));
                    humidityTextView.setTextColor(ContextCompat.getColor(MainActivity.this, android.R.color.black));
                    titleTextView3.setTextColor(ContextCompat.getColor(MainActivity.this, android.R.color.black));
                    humidityImageView.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.black));
                
                }
            }
        });





        final CardView cardView3 = findViewById(R.id.materialCardView3);
        final TextView routerTextView = findViewById(R.id.router_text);
        final TextView titleTextView4 = findViewById(R.id.title_text_view4);
        final ImageView routerImageView = findViewById(R.id.router);

        Switch switch3 = findViewById(R.id.switch3);

        switch3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                // Change card color, text color, and image color based on the switch state for switch3
                if (isChecked) {
                    cardView3.setCardBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.blue));
                    routerTextView.setTextColor(ContextCompat.getColor(MainActivity.this, android.R.color.white));
                    titleTextView4.setTextColor(ContextCompat.getColor(MainActivity.this, android.R.color.white));
                    routerImageView.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.white));
                } else {
                    cardView3.setCardBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.white));
                    routerTextView.setTextColor(ContextCompat.getColor(MainActivity.this, android.R.color.black));
                    titleTextView4.setTextColor(ContextCompat.getColor(MainActivity.this, android.R.color.black));
                    routerImageView.setColorFilter(ContextCompat.getColor(MainActivity.this, android.R.color.black));
                }
            }
        });




        final CardView cardView5 = findViewById(R.id.materialCardView5);
        final TextView nodesTextView = findViewById(R.id.title_text_view8);
        final TextView roomHueTextView = findViewById(R.id.title_text_view9);
        final ImageView nodesImageView = findViewById(R.id.nodes);

        Switch switch5 = findViewById(R.id.switch5);

        switch5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                // Change card color, text color, and image color based on the switch state for switch5
                if (isChecked) {
                    cardView5.setCardBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.blue));
                    nodesTextView.setTextColor(ContextCompat.getColor(MainActivity.this, android.R.color.white));
                    roomHueTextView.setTextColor(ContextCompat.getColor(MainActivity.this, android.R.color.white));
                    // Change ImageView color to white
                    nodesImageView.setColorFilter(ContextCompat.getColor(MainActivity.this, android.R.color.white));
                } else {
                    cardView5.setCardBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.white));
                    nodesTextView.setTextColor(ContextCompat.getColor(MainActivity.this, android.R.color.black));
                    roomHueTextView.setTextColor(ContextCompat.getColor(MainActivity.this, android.R.color.black));
                    // Change ImageView color to white
                    nodesImageView.setColorFilter(ContextCompat.getColor(MainActivity.this, android.R.color.black));
                }
            }
        });

        final CardView cardView6 = findViewById(R.id.materialCardView6);
        final TextView dataVisTextView = findViewById(R.id.title_text_view10);
        final TextView pieBarsTextView = findViewById(R.id.title_text_view2);
        final ImageView pieChartImageView = findViewById(R.id.pie_chart);

        Switch switch6 = findViewById(R.id.switch6);

        switch6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                // Change card color, text color, and image color based on the switch state for switch6
                if (isChecked) {
                    cardView6.setCardBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.blue));
                    dataVisTextView.setTextColor(ContextCompat.getColor(MainActivity.this, android.R.color.white));
                    pieBarsTextView.setTextColor(ContextCompat.getColor(MainActivity.this, android.R.color.white));
                    // Change ImageView color to white
                    pieChartImageView.setColorFilter(ContextCompat.getColor(MainActivity.this, android.R.color.white));
                } else {
                    cardView6.setCardBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.white));
                    dataVisTextView.setTextColor(ContextCompat.getColor(MainActivity.this, android.R.color.black));
                    pieBarsTextView.setTextColor(ContextCompat.getColor(MainActivity.this, android.R.color.black));
                    // Change ImageView color to white
                    pieChartImageView.setColorFilter(ContextCompat.getColor(MainActivity.this, android.R.color.black));
                }
            }
        });








    }



}

package com.example.androidstudioplanty;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class water_analysis extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_analysis);



        ImageView imageViewHome = findViewById(R.id.imageViewHome);
        imageViewHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start HomeActivity
                Intent intent = new Intent(water_analysis.this, MainActivity.class);
                startActivity(intent);
            }
        });

        ImageView imageViewManageAccounts = findViewById(R.id.imageViewManageAccounts);
        imageViewManageAccounts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Open ManageAccountsActivity
                Intent intent = new Intent(water_analysis.this, ManageAccounts.class);
                startActivity(intent);
            }
        });



    }


}
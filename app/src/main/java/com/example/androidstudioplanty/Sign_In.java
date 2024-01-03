package com.example.androidstudioplanty;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Sign_In extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        // Initialize UI components
        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.passwordEditText);
        Button loginButton = findViewById(R.id.loginButton);

        // Set click listener for the login button
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get entered username and password
                String enteredUsername = usernameEditText.getText().toString().trim();
                String enteredPassword = passwordEditText.getText().toString().trim();

                // Check if the username and password match the predefined values
                if (enteredUsername.equals("admin") && enteredPassword.equals("admin")) {
                    // Successful login, navigate to the main activity
                    Intent intent = new Intent(Sign_In.this, MainActivity.class);
                    startActivity(intent);
                    finish(); // Close the current activity
                } else {
                    // Failed login, display an error message
                    Toast.makeText(Sign_In.this, "Wrong username or password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

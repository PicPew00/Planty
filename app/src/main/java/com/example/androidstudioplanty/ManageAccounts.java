package com.example.androidstudioplanty;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ManageAccounts extends AppCompatActivity {
    private static final int REQUEST_CODE_ADD_ACCOUNT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_accounts);

        Button btnAddAccount = findViewById(R.id.btnAddAccount);
        btnAddAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ManageAccounts.this, dialog_add_account.class);
                startActivityForResult(intent, REQUEST_CODE_ADD_ACCOUNT);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_ADD_ACCOUNT && resultCode == RESULT_OK) {
            String username = data.getStringExtra("username");
            String password = data.getStringExtra("password");
            String permission = data.getStringExtra("permission");

            displayAccount(username, password, permission);
        }
    }

    private void displayAccount(String username, String password, String permission) {
        // Inflate the account item layout
        View accountView = getLayoutInflater().inflate(R.layout.activity_account_item, null);

        // Find the TextViews in the inflated view
        TextView textUsername = accountView.findViewById(R.id.textUsername);
        TextView textPermission = accountView.findViewById(R.id.textPermission);

        // Set the account data to the TextViews
        textUsername.setText("Username: " + username);
        textPermission.setText("Permission: " + permission);

        // Create LayoutParams for the margin
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

        // Set the margin programmatically (adjust the values as needed)
        layoutParams.setMargins(0, 0, 0, 16); // left, top, right, bottom

        // Apply the LayoutParams to the inflated view
        accountView.setLayoutParams(layoutParams);

        // Add the inflated view (account item) to the accountsLayout
        LinearLayout accountsLayout = findViewById(R.id.accountsLayout);
        accountsLayout.addView(accountView);
    }


}

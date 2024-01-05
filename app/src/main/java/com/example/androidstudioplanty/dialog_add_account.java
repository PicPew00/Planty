
package com.example.androidstudioplanty;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidstudioplanty.R;

public class dialog_add_account extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showAddAccountDialog();
    }

    private void showAddAccountDialog() {
        // Inflate the custom layout
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.activity_dialog_add_account, null);

        // Find views in the custom layout
        EditText editTextUsername = dialogView.findViewById(R.id.editTextUsername);
        EditText editTextPassword = dialogView.findViewById(R.id.editTextPassword);
        Spinner spinnerPermissions = dialogView.findViewById(R.id.spinnerPermissions);

        // Dummy array for demonstration purposes
        String[] dummyPermissions = {"High", "Medium", "Low"};

        // Set up the spinner with dummy permissions
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                dummyPermissions
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPermissions.setAdapter(adapter);

        // Create the AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Account")
                .setView(dialogView)
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Handle the positive button click (Save button)
                        String username = editTextUsername.getText().toString();
                        String password = editTextPassword.getText().toString();
                        String selectedPermission = spinnerPermissions.getSelectedItem().toString();

                        // Send the data back to the calling activity
                        Intent resultIntent = new Intent();
                        resultIntent.putExtra("username", username);
                        resultIntent.putExtra("password", password);
                        resultIntent.putExtra("permission", selectedPermission);
                        setResult(RESULT_OK, resultIntent);

                        // Finish the activity
                        finish();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                        finish();
                    }
                });

        // Show the AlertDialog
        builder.create().show();
    }
}

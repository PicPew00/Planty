package com.example.androidstudioplanty;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;


public class AlarmDialog extends AlertDialog {
    private static final String PREF_TEMPERATURE_LIMIT = "pref_temperature_limit";
    private static final String PREF_HUMIDITY_LIMIT = "pref_humidity_limit";
    private static final String PREF_TEMPERATURE_CHECKED = "pref_temperature_checked";
    private static final String PREF_HUMIDITY_CHECKED = "pref_humidity_checked";
    private Context context;
    private View anchorView; // The view to anchor the popup window

    public AlarmDialog(Context context, View anchorView) {
        super(context);
        this.context = context;
        this.anchorView = anchorView; // Set the anchor view
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);

        // Retrieve saved values and states
        String savedTemperatureLimit = prefs.getString(PREF_TEMPERATURE_LIMIT, "");
        String savedHumidityLimit = prefs.getString(PREF_HUMIDITY_LIMIT, "");
        boolean savedTemperatureChecked = prefs.getBoolean(PREF_TEMPERATURE_CHECKED, false);
        boolean savedHumidityChecked = prefs.getBoolean(PREF_HUMIDITY_CHECKED, false);

        // Set saved values and states in the text boxes and checkboxes

        // Get the layout from the resource
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.activity_dialog_add_alarm, null);

        // Convert 200dp to pixels
        float scale = context.getResources().getDisplayMetrics().density;
        int width = (int) (320 * scale + 0.5f); // 200dp converted to pixels
        int height = (int) (270 * scale + 0.5f);;

        PopupWindow popupWindow = new PopupWindow(dialogView, width, height, true);


        // Initialize the views
        final EditText editTextTemperatureLimit = dialogView.findViewById(R.id.editTextTemperatureLimit);
        final EditText editTextHumidityLimit = dialogView.findViewById(R.id.editTextHumidityLimit);
        final CheckBox checkBoxTemperature = dialogView.findViewById(R.id.checkBoxTemperature);
        final CheckBox checkBoxHumidity = dialogView.findViewById(R.id.checkBoxHumidity);

        // Set initial state
        editTextTemperatureLimit.setText(savedTemperatureLimit);
        editTextHumidityLimit.setText(savedHumidityLimit);
        checkBoxTemperature.setChecked(savedTemperatureChecked);
        checkBoxHumidity.setChecked(savedHumidityChecked);
        editTextTemperatureLimit.setEnabled(savedTemperatureChecked);
        editTextHumidityLimit.setEnabled(savedHumidityChecked);

        // Add listeners to checkboxes to enable/disable textboxes
        checkBoxTemperature.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                editTextTemperatureLimit.setEnabled(isChecked);
            }
        });

        checkBoxHumidity.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                editTextHumidityLimit.setEnabled(isChecked);
            }
        });

        Button buttonSave = dialogView.findViewById(R.id.buttonSave);
        Button buttonCancel = dialogView.findViewById(R.id.buttonCancel);

        // Set saved values in the text boxes
        editTextTemperatureLimit.setText(savedTemperatureLimit);
        editTextHumidityLimit.setText(savedHumidityLimit);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get values and states from text boxes and checkboxes
                String temperatureLimit = editTextTemperatureLimit.getText().toString();
                String humidityLimit = editTextHumidityLimit.getText().toString();
                boolean isTemperatureChecked = checkBoxTemperature.isChecked();
                boolean isHumidityChecked = checkBoxHumidity.isChecked();

                // Save values and states to shared preferences
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString(PREF_TEMPERATURE_LIMIT, temperatureLimit);
                editor.putString(PREF_HUMIDITY_LIMIT, humidityLimit);
                editor.putBoolean(PREF_TEMPERATURE_CHECKED, isTemperatureChecked);
                editor.putBoolean(PREF_HUMIDITY_CHECKED, isHumidityChecked);
                editor.apply();

                // Dismiss the popup window
                popupWindow.dismiss();
            }
        });
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // User cancelled the dialog
                popupWindow.dismiss();
            }
        });


        // Show the popup window at the specified location
        if (anchorView != null) {
            popupWindow.showAtLocation(anchorView, Gravity.CENTER, 0, 0);
        }
    }
}

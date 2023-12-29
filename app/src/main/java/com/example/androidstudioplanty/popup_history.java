package com.example.androidstudioplanty;


import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

public class popup_history extends AppCompatActivity {

    private RequestQueue requestQueue;
    private Gson gson;

    private CalendarView calendarView;
    private Button fetchButton;
    private Button closeButton;
    private TextView historyResultsTextView;
    private PopupWindow popupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_history);

        requestQueue = Volley.newRequestQueue(this);

        calendarView = findViewById(R.id.calendarView);
        fetchButton = findViewById(R.id.fetchButton);
        closeButton = findViewById(R.id.closeButton);
        historyResultsTextView = findViewById(R.id.historyResultsTextView);


        // Set a click listener for the fetch button
        fetchButton.setOnClickListener(v -> {
            // Fetch history data based on selected date range
            String selectedDate = formatDate(calendarView.getDate());
            getHistoryData(selectedDate);
        });

        // Set a click listener for the close button
        closeButton.setOnClickListener(v -> {
            // Close the popup_history activity
            finish();
        });
    }
    private void getHistoryData(String selectedDate) {
        String ENDPOINT = "https://mdl.frederick.ac.cy/cyriciotwebapis/api/Data/GetLineChartSingleSensorData";

        JSONObject requestBody = new JSONObject();
        try {
            // Adjust the request body to match your API requirements
            requestBody.put("id", 96);
            requestBody.put("fromDate", selectedDate);
            requestBody.put("toDate", selectedDate);

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, ENDPOINT, requestBody,
                    response -> {
                        gson = new Gson();
                        // Handle the API response and update historyResultsTextView
                        // You can parse the JSON response and set it to historyResultsTextView.
                    },
                    error -> {
                        historyResultsTextView.setText("Error fetching history data");
                    }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<String, String>();
                    headers.put("Content-Type", "application/json");
                    headers.put("Authorization", "Bearer your_access_token");
                    return headers;
                }
            };

            requestQueue.add(request);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private String formatDate(long timestamp) {
        // Convert timestamp to Date object
        Date date = new Date(timestamp);

        // Extract year, month, and day of month from the date object
        int year = date.getYear();
        int month = date.getMonth() + 1; // Month is zero-indexed, so add 1
        int dayOfMonth = date.getDate();

        // Format the date string using the chosen format
        String formattedDate = String.format("YYYY-MM-dd", year, month, dayOfMonth);
        return formattedDate;
    }


}

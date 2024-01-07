package com.example.androidstudioplanty;

import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.androidstudioplanty.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.gson.Gson;
import com.robinhood.spark.SparkAdapter;
import com.robinhood.spark.SparkView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class data_visual extends AppCompatActivity {

    private String selectedStartingDate;
    private String selectedEndingDate;
    private Gson gson;
    private RequestQueue requestQueue;
    private SparkView sparkView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_visual);

        gson = new Gson();
        requestQueue = Volley.newRequestQueue(this);

        MaterialButton button = findViewById(R.id.rangePicker);
        TextView startingDate = findViewById(R.id.startingDate);
        TextView endingDate = findViewById(R.id.endingDate);
        sparkView = findViewById(R.id.sparkView);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MaterialDatePicker<androidx.core.util.Pair<Long, Long>> materialDatePicker = MaterialDatePicker.Builder.dateRangePicker().setSelection(new androidx.core.util.Pair<>(
                        MaterialDatePicker.thisMonthInUtcMilliseconds(),
                        MaterialDatePicker.todayInUtcMilliseconds()
                )).build();

                materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<androidx.core.util.Pair<Long, Long>>() {
                    @Override
                    public void onPositiveButtonClick(androidx.core.util.Pair<Long, Long> selection) {
                        selectedStartingDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new java.util.Date(selection.first));
                        selectedEndingDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new java.util.Date(selection.second));

                        startingDate.setText(String.format("Selected Starting Date: %s", selectedStartingDate));
                        endingDate.setText(String.format("Selected Ending Date: %s", selectedEndingDate));

                        int sensorId = 96;

                        getSingleSensorData(sensorId);
                    }
                });

                materialDatePicker.show(getSupportFragmentManager(), "tag");
            }
        });
    }

    private void getSingleSensorData(int sensorId) {
        String ENDPOINT = "https://mdl.frederick.ac.cy/cyriciotwebapis/api/Data/GetLineChartSingleSensorData";

        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("id", sensorId);
            requestBody.put("fromDate", selectedStartingDate);
            requestBody.put("toDate", selectedEndingDate);

            Log.d("APIRequest", "Submitting API request with sensorId: " + sensorId);

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, ENDPOINT, requestBody,
                    response -> {
                        SingleValue sensorData = gson.fromJson(response.toString(), SingleValue.class);

                        List<Float> entries = new ArrayList<>();
                        if (sensorData != null && sensorData.getValues() != null) {
                            for (ValueItem valueItem : sensorData.getValues()) {
                                Double value = valueItem.getValue();
                                if (value != null) {
                                    entries.add(value.floatValue()); // Convert Double to Float
                                }
                            }
                        }


                        SparkAdapter sparkAdapter = new SparkAdapter() {
                            @Override
                            public int getCount() {
                                return entries.size();
                            }

                            @Override
                            public Object getItem(int index) {
                                return entries.get(index);
                            }

                            @Override
                            public float getY(int index) {
                                return entries.get(index);
                            }
                        };

                        sparkView.setAdapter(sparkAdapter);

                    },
                    error -> {
                        // Handle API error
                        Log.e("APIError", "Error occurred");
                        if (error.networkResponse != null) {
                            Log.e("APIError", "Status Code: " + error.networkResponse.statusCode);
                            Log.e("APIError", "Response Data: " + new String(error.networkResponse.data));
                        }
                    }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Content-Type", "application/json");
                    headers.put("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJhY3NjNDIzQGZyZWRlcmljay5hYy5jeSIsImp0aSI6ImY4ZmUzYmJmLTNjNDctNDBkMi05YzI1LWFjYjNkODIwODhmNiIsImVtYWlsIjoiYWNzYzQyM0BmcmVkZXJpY2suYWMuY3kiLCJ1aWQiOiIwODk1MDQ2NS01YmZlLTRjZjYtYjQwNS1mM2YyNjNjY2YxNDkiLCJSb2xlIjoiQ3VzdG9tZXIiLCJyb2xlcyI6IkN1c3RvbWVyIiwiZXhwIjoxNzM0NTE0MTcxLCJpc3MiOiJTZWN1cmVBcGkiLCJhdWQiOiJTZWN1cmVBcGlVc2VyIn0.LFkVpXuraX7r8xQJa_VNGMYzTx0LdGS5T0BAcrgt6TA");
                    headers.put("Cache-Control", "no-cache, no-store, must-revalidate");
                    headers.put("Pragma", "no-cache");
                    headers.put("Expires", "0");
                    return headers;
                }
            };

            requestQueue.add(request);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

package com.example.androidstudioplanty;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class airqualityrange extends AppCompatActivity {

    private String selectedStartingDate;
    private String selectedEndingDate;
    private Gson gson;
    private RequestQueue requestQueue;
    // Call the method to fetch data with the selected dates
    // Inside the onCreate method of popup_history activity



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_history);

        gson = new Gson();
        requestQueue = Volley.newRequestQueue(this);

        MaterialButton button = findViewById(R.id.rangePicker);
        TextView startingDate = findViewById(R.id.startingDate);
        TextView endingDate = findViewById(R.id.endingDate);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MaterialDatePicker<Pair<Long, Long>> materialDatePicker = MaterialDatePicker.Builder.dateRangePicker().setSelection(new Pair<>(
                        MaterialDatePicker.thisMonthInUtcMilliseconds(),
                        MaterialDatePicker.todayInUtcMilliseconds()
                )).build();
                materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Pair<Long, Long>>() {
                    @Override
                    public void onPositiveButtonClick(Pair<Long, Long> selection) {
                        selectedStartingDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date(selection.first));
                        selectedEndingDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date(selection.second));

                        startingDate.setText(MessageFormat.format("Selected Starting Date: {0}", selectedStartingDate));
                        endingDate.setText(MessageFormat.format("Selected Ending Date: {0}", selectedEndingDate));

                        Intent intent = getIntent();
                        int sensorId = intent.getIntExtra("sensorId", -1);
                        Log.d("SensorId", "Received sensorId: " + sensorId);

                        TextView resultTextView = findViewById(R.id.resultTextView); // Replace with your TextView
                        getSingleSensorData(sensorId, resultTextView);
                    }
                });

                materialDatePicker.show(getSupportFragmentManager(), "tag");
            }
        });
    }

    private void getSingleSensorData(int sensorId, TextView resultTextView) {
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

                        List<ValueItem> values = sensorData.getValues();
                        if (values != null && !values.isEmpty()) {
                            for (ValueItem valueItem : values) {
                                Log.d("ValueItem", "Date: " + valueItem.getDate() + ", Value: " + valueItem.getValue());
                            }

                            LinearLayout linearLayout = findViewById(R.id.scrollViewContainer);
                            for (ValueItem valueItem : values) {
                                LinearLayout valueLayout = new LinearLayout(this);
                                valueLayout.setOrientation(LinearLayout.VERTICAL);

                                TextView valueTextView = new TextView(this);
                                valueTextView.setText(String.format("%s%s", valueItem.getValue(), sensorData.getUnit()));
                                valueTextView.setBackgroundResource(R.drawable.rounded_corner2);
                                valueTextView.setPadding(16, 16, 16, 16);
                                valueTextView.setGravity(Gravity.CENTER);

                                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                                        LinearLayout.LayoutParams.MATCH_PARENT,
                                        LinearLayout.LayoutParams.WRAP_CONTENT
                                );
                                layoutParams.gravity = Gravity.CENTER;
                                valueTextView.setLayoutParams(layoutParams);

                                TextView timestampTextView = new TextView(this);
                                try {
                                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                                    Date date = valueItem.getDateAsDate();
                                    String formattedTimestamp = dateFormat.format(date);
                                    timestampTextView.setText(formattedTimestamp);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                                valueLayout.addView(valueTextView);
                                valueLayout.addView(timestampTextView);

                                linearLayout.addView(valueLayout);
                            }
                        } else {
                            resultTextView.setText("No data available");
                            Log.d("ValueItem", "List is empty or null");
                        }

                    },
                    error -> {
                        resultTextView.setText(error.getMessage());

                        // Additional log message to check if the error block is executed
                        Log.e("APIError", "Error occurred");

                        if (error.networkResponse != null) {
                            Log.e("APIError", "Status Code: " + error.networkResponse.statusCode);
                            Log.e("APIError", "Response Data: " + new String(error.networkResponse.data));
                        }
                    }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<String, String>();
                    headers.put("Content-Type", "application/json");
                    headers.put("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJhY3NjNDIzQGZyZWRlcmljay5hYy5jeSIsImp0aSI6ImY4ZmUzYmJmLTNjNDctNDBkMi05YzI1LWFjYjNkODIwODhmNiIsImVtYWlsIjoiYWNzYzQyM0BmcmVkZXJpY2suYWMuY3kiLCJ1aWQiOiIwODk1MDQ2NS01YmZlLTRjZjYtYjQwNS1mM2YyNjNjY2YxNDkiLCJSb2xlIjoiQ3VzdG9tZXIiLCJyb2xlcyI6IkN1c3RvbWVyIiwiZXhwIjoxNzM0NTE0MTcxLCJpc3MiOiJTZWN1cmVBcGkiLCJhdWQiOiJTZWN1cmVBcGlVc2VyIn0.LFkVpXuraX7r8xQJa_VNGMYzTx0LdGS5T0BAcrgt6TA");
                    // Set cache control headers
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






package com.example.androidstudioplanty;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AirQuality extends AppCompatActivity {

    private RequestQueue requestQueue;
    private Gson gson;



    private TextView result79,result80,result70,result71;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_air_quality);

        result79 = findViewById(R.id.textViewCafeteriaCO2Value); //Caffe CO2
        result80 = findViewById(R.id.textViewCafeteriaTVOCValue); //Caffe TVOC


        result71 = findViewById(R.id.textViewTVOCValue); //MDL TVOC Quality
        result70 = findViewById(R.id.textViewCO2Value); //MDL CO2




        requestQueue = Volley.newRequestQueue(this);

        //MDL
        getSingleSensorData(79, result79);
        getSingleSensorData(80, result80);
        getSingleSensorData(70, result70);
        getSingleSensorData(71, result71);


        ImageView historyIcon96 = findViewById(R.id.sensorId99);
        historyIcon96.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Set the appropriate sensorId (e.g., 96 for sensorId96)
                int sensorId = 99;

                // Open the activity_popup_history layout
                Intent intent = new Intent(AirQuality.this, popup_history.class);

                // Put the sensorId as an extra in the intent
                intent.putExtra("sensorId", sensorId);

                startActivity(intent);
            }
        });


        ImageView imageViewHome = findViewById(R.id.imageViewHome);
        imageViewHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start MainActivity
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

    }
    private void getSingleSensorData(int sensorId, TextView resultTextView) {
        String ENDPOINT = "https://mdl.frederick.ac.cy/cyriciotwebapis/api/Data/GetCardWidgetData"; // Replace with your API endpoint

        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("id", sensorId);

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, ENDPOINT, requestBody,
                    response -> {
                        gson = new Gson();
                        SingleValue sensorData = gson.fromJson(response.toString(), SingleValue.class);
                        resultTextView.setText(String.format("%s%s", sensorData.getValue(), sensorData.getUnit()));
                    },
                    error -> {
                        resultTextView.setText(error.getMessage());
                    }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<String, String>();
                    headers.put("Content-Type", "application/json");
                    headers.put("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJhY3NjNDIzQGZyZWRlcmljay5hYy5jeSIsImp0aSI6ImY4ZmUzYmJmLTNjNDctNDBkMi05YzI1LWFjYjNkODIwODhmNiIsImVtYWlsIjoiYWNzYzQyM0BmcmVkZXJpY2suYWMuY3kiLCJ1aWQiOiIwODk1MDQ2NS01YmZlLTRjZjYtYjQwNS1mM2YyNjNjY2YxNDkiLCJSb2xlIjoiQ3VzdG9tZXIiLCJyb2xlcyI6IkN1c3RvbWVyIiwiZXhwIjoxNzM0NTE0MTcxLCJpc3MiOiJTZWN1cmVBcGkiLCJhdWQiOiJTZWN1cmVBcGlVc2VyIn0.LFkVpXuraX7r8xQJa_VNGMYzTx0LdGS5T0BAcrgt6TA");
                    return headers;
                }
            };

            requestQueue.add(request);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



}

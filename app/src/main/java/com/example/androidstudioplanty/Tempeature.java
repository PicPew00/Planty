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

public class Tempeature extends AppCompatActivity {


    private RequestQueue requestQueue;
    private Gson gson;


    private TextView result67;
    private TextView result68;
    private TextView result76 ,result77 ;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tempeature);
        result68 = findViewById(R.id.resultMDL68); //MDL Humidity
        result67 = findViewById(R.id.resultMDL67); //MDL Tempeatuere

        result76 = findViewById(R.id.resultcaffe76);//Temp
        result77 = findViewById(R.id.resultcaffe77);//hummi

        requestQueue = Volley.newRequestQueue(this);

        //MDL
        getSingleSensorData(68, result68);
        // Fetch data for ID 67
        getSingleSensorData(67, result67);

        //Caffeteria
        getSingleSensorData(76, result76);
        // Fetch data for ID 67
        getSingleSensorData(77, result77);


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
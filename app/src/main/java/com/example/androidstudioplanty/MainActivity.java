package com.example.androidstudioplanty;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

public class MainActivity extends AppCompatActivity {

    private RequestQueue requestQueue;
    private Gson gson;
    private static final String ENDPOINT = "https://mdl.frederick.ac.cy/cyriciotwebapis/api/Data/GetCardWidgetData";

    private TextView result;
    public void onCardClick(View view) {
        // Handle the card click event here
        Intent intent = new Intent(this, Tempeature.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tempeature);
        result = findViewById(R.id.result);
        requestQueue = Volley.newRequestQueue(this);
        getSingleSensorData();
    }

    private void getSingleSensorData()  {

        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("id",67);
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,ENDPOINT,requestBody, response -> {
                gson = new Gson();
                SingleValue sensorData = gson.fromJson(response.toString(),SingleValue.class);
                result.setText(String.format("Value: %s%s", sensorData.getValue(), sensorData.getUnit()));
            },error -> {
                result.setText(error.getMessage());
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
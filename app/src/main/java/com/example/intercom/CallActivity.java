package com.example.intercom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class CallActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);
    }

    public void onOpenClc(View view) throws IOException, JSONException {
        sharedPreferences = getSharedPreferences("inter_data", Context.MODE_PRIVATE);

        URL url = new URL("http://89.208.220.227:82/call");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("POST");

        conn.setRequestProperty("accept", "application/json");
        conn.setRequestProperty("flat", sharedPreferences.getString("flat", ""));
        conn.setRequestProperty("house", sharedPreferences.getString("house", ""));

        conn.setRequestProperty("Content-Type", "application/json");
        JSONObject content = new JSONObject("open");


        if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
            throw new RuntimeException("Request Failed. HTTP Error Code: " + conn.getResponseCode());
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuffer jsonString = new StringBuffer();
        String line;
        while ((line = br.readLine()) != null) {
            jsonString.append(line);
        }
        line = jsonString.substring(10, 33);
        br.close();
        conn.disconnect();
    }
}
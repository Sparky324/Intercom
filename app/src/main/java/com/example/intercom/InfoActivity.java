package com.example.intercom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class InfoActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        sharedPreferences = getSharedPreferences("inter_data", Context.MODE_PRIVATE);

        TextView model = (TextView)findViewById(R.id.text_intercom_model);

        model.setText(sharedPreferences.getString("model", ""));

    }

    public void onSettClc(View view) {
        Intent intent = new Intent(InfoActivity.this, SetupActivity.class);
        startActivity(intent);
    }

    public void onRetryClc(View view) {

    }

    public void onHistClc(View view) {
        Intent intent = new Intent(InfoActivity.this, HistActivity.class);
        startActivity(intent);
    }
}
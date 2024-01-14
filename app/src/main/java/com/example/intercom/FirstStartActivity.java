package com.example.intercom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class FirstStartActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_start);

        sharedPreferences = getSharedPreferences("inter_data", Context.MODE_PRIVATE);
        Set<String> set = sharedPreferences.getStringSet("items", new HashSet<>());
        ArrayList<String> items = new ArrayList<>(set);

        if (!items.isEmpty()) {
            Intent intent = new Intent(FirstStartActivity.this, InfoActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }

    public void onLetsClc(View view) {
        Intent intent = new Intent(FirstStartActivity.this, SetupActivity.class);
        startActivity(intent);
    }
}
package com.example.intercom;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class FirstStartActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_start);

        if (!isOnline()) {
            Intent intent = new Intent(FirstStartActivity.this, NoConnetionActivity.class);
            startActivity(intent);
            finish();
        }

        sharedPreferences = this.getSharedPreferences("inter_data", Context.MODE_PRIVATE);
        Set<String> set = sharedPreferences.getStringSet("items", new HashSet<>());
        ArrayList<String> items = new ArrayList<>(set);

        if (!items.isEmpty()) {
            Intent intent = new Intent(FirstStartActivity.this, InfoActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public void onLetsClc(View view) {
        Intent intent = new Intent(FirstStartActivity.this, SetupActivity.class);
        startActivity(intent);
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
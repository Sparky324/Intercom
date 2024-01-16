package com.example.intercom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class NoConnetionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_connetion);
    }

    public void onRetryClc(View view) {
        Intent intent = new Intent(NoConnetionActivity.this, FirstStartActivity.class);
        startActivity(intent);
        finish();
    }
}
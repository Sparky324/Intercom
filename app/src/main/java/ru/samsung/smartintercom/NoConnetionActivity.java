package ru.samsung.smartintercom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import ru.samsung.smartintercom.R;

import java.util.Objects;

import ru.samsung.smartintercom.ui.activity.MainActivity;

public class NoConnetionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_connetion);
        Objects.requireNonNull(getSupportActionBar()).hide();
    }

    public void onRetryClc(View view) {
        Intent intent = new Intent(NoConnetionActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void onHistClc(View view) {
        Intent intent = new Intent(NoConnetionActivity.this, HistActivity.class);
        startActivity(intent);
    }
}
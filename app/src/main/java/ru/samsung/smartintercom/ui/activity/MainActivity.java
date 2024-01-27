package ru.samsung.smartintercom.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;

import ru.samsung.smartintercom.R;

import java.util.Objects;

import ru.samsung.smartintercom.InfoActivity;
import ru.samsung.smartintercom.NoConnetionActivity;
import ru.samsung.smartintercom.SetupActivity;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).hide();

        if (!isOnline()) {
            Intent intent = new Intent(MainActivity.this, NoConnetionActivity.class);
            startActivity(intent);
            finish();
        }

        sharedPreferences = this.getSharedPreferences("inter_data", Context.MODE_PRIVATE);

        if (!sharedPreferences.getString("flat", "").isEmpty()) {
            Intent intent = new Intent(MainActivity.this, InfoActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public void onLetsClc(View view) {
        Intent intent = new Intent(MainActivity.this, SetupActivity.class);
        startActivity(intent);
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
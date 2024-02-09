package ru.samsung.smartintercom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


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
        if (!isOnline()) {
            Toast.makeText(NoConnetionActivity.this, "Подключения нет, попробуйте позже или проверьте связь.", Toast.LENGTH_LONG).show();
        } else {
            Intent intent = new Intent(NoConnetionActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public void onHistClc(View view) {
        Intent intent = new Intent(NoConnetionActivity.this, HistActivity.class);
        startActivity(intent);
    }
}
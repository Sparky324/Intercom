package ru.samsung.smartintercom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Toast;

import ru.samsung.smartintercom.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class CallActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);
        Objects.requireNonNull(getSupportActionBar()).hide();

        StrictMode.ThreadPolicy gfgPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(gfgPolicy);
    }

    public void onOpenClc(View view) throws IOException, JSONException {
        sharedPreferences = getSharedPreferences("inter_data", Context.MODE_PRIVATE);

        URL url = new URL("http://89.208.220.227:82/call");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("POST");
        conn.setDoOutput(true);

        conn.setRequestProperty("accept", "application/json");
        conn.setRequestProperty("flat", sharedPreferences.getString("flat", ""));
        conn.setRequestProperty("house", sharedPreferences.getString("house", ""));

        conn.setRequestProperty("Content-Type", "application/json");
        JSONObject content = new JSONObject();
        content.put("status", "open");

        OutputStream os = conn.getOutputStream();
        OutputStreamWriter osw = new OutputStreamWriter(os, StandardCharsets.UTF_8);

        osw.write(content.toString());
        osw.flush();
        osw.close();
        os.close();


        int HttpResult = conn.getResponseCode();
        if (HttpResult == HttpURLConnection.HTTP_OK) {
            Toast.makeText(CallActivity.this, "Дверь открыта!", Toast.LENGTH_LONG).show();
            conn.disconnect();
            finish();
        } else {
            Toast.makeText(CallActivity.this, "Что-то пошло не так...", Toast.LENGTH_LONG).show();
        }

    }

    public void onCloseClc(View view) throws IOException, JSONException {
        sharedPreferences = getSharedPreferences("inter_data", Context.MODE_PRIVATE);

        URL url = new URL("http://89.208.220.227:82/call");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("POST");
        conn.setDoOutput(true);

        conn.setRequestProperty("accept", "application/json");
        conn.setRequestProperty("flat", sharedPreferences.getString("flat", ""));
        conn.setRequestProperty("house", sharedPreferences.getString("house", ""));

        conn.setRequestProperty("Content-Type", "application/json");
        JSONObject content = new JSONObject();
        content.put("status", "close");

        OutputStream os = conn.getOutputStream();
        OutputStreamWriter osw = new OutputStreamWriter(os, StandardCharsets.UTF_8);

        osw.write(content.toString());
        osw.flush();
        osw.close();
        os.close();


        int HttpResult = conn.getResponseCode();
        if (HttpResult == HttpURLConnection.HTTP_OK) {
            Toast.makeText(CallActivity.this, "Дверь закрыта!", Toast.LENGTH_LONG).show();
            conn.disconnect();
            finish();
        } else {
            Toast.makeText(CallActivity.this, "Что-то пошло не так...", Toast.LENGTH_LONG).show();
        }

    }
}
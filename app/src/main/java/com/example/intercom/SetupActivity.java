package com.example.intercom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class SetupActivity extends AppCompatActivity implements android.text.TextWatcher {

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        StrictMode.ThreadPolicy gfgPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(gfgPolicy);

        sharedPreferences = getSharedPreferences("inter_data", Context.MODE_PRIVATE);

        EditText editText = (EditText)findViewById(R.id.input_house);
        EditText editText1 = (EditText)findViewById(R.id.input_flat);
        Button button = (Button)findViewById(R.id.button_save);

        button.setEnabled(false);

        editText.addTextChangedListener(this);
        editText1.addTextChangedListener(this);

        editText.setText(sharedPreferences.getString("house", ""));
        editText1.setText(sharedPreferences.getString("flat", ""));
    }

    public void onSaveClc(View view) throws IOException {
        sharedPreferences = getSharedPreferences("inter_data", Context.MODE_PRIVATE);

        EditText editText = (EditText)findViewById(R.id.input_house);
        EditText editText1 = (EditText)findViewById(R.id.input_flat);

        String txt = editText.getText().toString();
        String txt1 = editText1.getText().toString();

        URL url = new URL("http://89.208.220.227:82/info");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestProperty("accept", "application/json");
        conn.setRequestProperty("flat", txt1);
        conn.setRequestProperty("house", txt);

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

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("flat", txt1);
        editor.putString("house", txt);
        editor.putString("model", line);
        editor.apply();

        Intent intent = new Intent(SetupActivity.this, InfoActivity.class);
        startActivity(intent);
        finish();
    }

    public void onExitClc(View view) {
        finish();
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        EditText editText = (EditText)findViewById(R.id.input_house);
        EditText editText1 = (EditText)findViewById(R.id.input_flat);
        Button button = (Button)findViewById(R.id.button_save);

        String txt = editText.getText().toString();
        String txt1 = editText1.getText().toString();

        if (txt.length() != 0 && txt1.length() != 0) {
            if (txt.length() <= 4 && Character.isDigit(txt.charAt(0)) &&
                    txt.length() - txt.replace("/", "").length() <= 1) {

                if (txt1.length() <= 6) {
                    button.setEnabled(true);
                } else {
                    button.setEnabled(false);
                }
            } else {
                button.setEnabled(false);
            }
        } else {
            button.setEnabled(false);
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
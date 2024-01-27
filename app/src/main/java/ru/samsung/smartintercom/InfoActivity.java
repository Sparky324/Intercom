package ru.samsung.smartintercom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import ru.samsung.smartintercom.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

public class InfoActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        Objects.requireNonNull(getSupportActionBar()).hide();

        StrictMode.ThreadPolicy gfgPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(gfgPolicy);

        sharedPreferences = getSharedPreferences("inter_data", Context.MODE_PRIVATE);

        TextView model = (TextView)findViewById(R.id.text_intercom_model);

        model.setText(sharedPreferences.getString("model", ""));

        getImage();

    }

    public void onSettClc(View view) {
        Intent intent = new Intent(InfoActivity.this, SetupActivity.class);
        startActivity(intent);
    }

    public void onRetryClc(View view) {

    }

    public void onRetryImageClc(View view) {
        getImage();
    }

    public void onHistClc(View view) {
        Intent intent = new Intent(InfoActivity.this, HistActivity.class);
        startActivity(intent);
    }

    private void getImage() {
        URL url1 = null;
        try {
            url1 = new URL("http://89.208.220.227:82/image");
            HttpURLConnection conn1 = (HttpURLConnection) url1.openConnection();
            conn1.setRequestProperty("accept", "application/json");
            conn1.setRequestProperty("flat", sharedPreferences.getString("flat", ""));
            conn1.setRequestProperty("house", sharedPreferences.getString("house", ""));

            if (conn1.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("Request Failed. HTTP Error Code: " + conn1.getResponseCode());
            }

            InputStream instream = conn1.getInputStream();
            Bitmap bmp = BitmapFactory.decodeStream(instream);

            ImageView imageView = (ImageView)findViewById(R.id.image_intercom);
            imageView.setImageBitmap(bmp);
            instream.close();
            conn1.disconnect();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
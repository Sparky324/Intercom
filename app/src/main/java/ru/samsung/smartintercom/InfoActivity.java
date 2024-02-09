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
import android.widget.Toast;

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
    }

    public void onSettClc(View view) {
        Intent intent = new Intent(InfoActivity.this, SetupActivity.class);
        startActivity(intent);
    }

    public void onRetryClc(View view) {
        sharedPreferences = getSharedPreferences("inter_data", Context.MODE_PRIVATE);

        URL url = null;
        try {
            url = new URL("http://89.208.220.227:82/info");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("accept", "application/json");
            conn.setRequestProperty("flat", sharedPreferences.getString("flat", ""));
            conn.setRequestProperty("house", sharedPreferences.getString("house", ""));

            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("Request Failed. HTTP Error Code: " + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuffer jsonString = new StringBuffer();
            String line;
            while ((line = br.readLine()) != null) {
                jsonString.append(line);
            }
            line = jsonString.toString();
            line = line.replace("\"", "");
            line = line.replace("model", "");
            line = line.replace(":", "");
            line = line.replace("{", "");
            line = line.replace("}", "");
            br.close();
            conn.disconnect();

            TextView model = (TextView)findViewById(R.id.text_intercom_model);
            model.setText(line);

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("model", line);
            editor.apply();
        } catch (IOException e) {
            Intent intent = new Intent(InfoActivity.this, NoConnetionActivity.class);
            startActivity(intent);
            finish();
        } catch (RuntimeException e) {
            Toast.makeText(InfoActivity.this, "Что-то пошло не так. \n Проверьте номер дома и квартиры", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
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
            Intent intent = new Intent(InfoActivity.this, NoConnetionActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
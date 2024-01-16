package com.example.intercom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class SetupActivity extends AppCompatActivity implements android.text.TextWatcher {

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        sharedPreferences = getSharedPreferences("inter_data", Context.MODE_PRIVATE);
        Set<String> set = sharedPreferences.getStringSet("items", new HashSet<>());
        ArrayList<String> items = new ArrayList<>(set);

        EditText editText = (EditText)findViewById(R.id.input_house);
        EditText editText1 = (EditText)findViewById(R.id.input_flat);
        Button button = (Button)findViewById(R.id.button_save);

        button.setEnabled(false);

        editText.addTextChangedListener(this);
        editText1.addTextChangedListener(this);

        if (items.isEmpty()) {
            editText.setText("Введите номер дома");
            editText1.setText("Введите номер квартиры");
        } else {
            editText.setText(items.get(0));
            editText1.setText(items.get(1));
        }
    }

    public void onSaveClc(View view) {
        sharedPreferences = getSharedPreferences("inter_data", Context.MODE_PRIVATE);
        Set<String> set = sharedPreferences.getStringSet("items", new HashSet<>());
        ArrayList<String> items = new ArrayList<>(set);

        EditText editText = (EditText)findViewById(R.id.input_house);
        EditText editText1 = (EditText)findViewById(R.id.input_flat);

        String txt = editText.getText().toString();
        String txt1 = editText1.getText().toString();

        items.add(txt);
        items.add(txt1);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        Set<String> set1 = new HashSet<>(items);
        editor.putStringSet("items", set1);
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
                    txt.length() - txt.replace("/", "").length() <= 1 &&
                    Character.isAlphabetic(txt.charAt(txt.length()-1))) {

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
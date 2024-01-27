package ru.samsung.smartintercom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.Bundle;
import android.view.View;

import ru.samsung.smartintercom.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HistActivity extends AppCompatActivity {
    AppDataBase db;
    RecyclerViewAdapter recyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hist);
        Objects.requireNonNull(getSupportActionBar()).hide();

        db = Room.databaseBuilder(getApplicationContext(), AppDataBase.class, "call").allowMainThreadQueries().build();
        List<Call> list = db.userDao().getAll();

        ArrayList<Call> arrList = new ArrayList<Call>(list);

        recyclerViewAdapter = new RecyclerViewAdapter(this, arrList);

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(recyclerViewAdapter);

    }

    public void onExtClc(View view) {
        finish();
    }
}
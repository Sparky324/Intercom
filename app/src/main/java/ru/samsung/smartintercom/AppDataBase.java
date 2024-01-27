package ru.samsung.smartintercom;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import ru.samsung.smartintercom.Call;

@Database(entities = {Call.class}, version = 1)
public abstract class AppDataBase extends RoomDatabase {
    public abstract CallDAO userDao();
}
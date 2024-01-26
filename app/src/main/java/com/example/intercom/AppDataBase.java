package com.example.intercom;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Call.class}, version = 1)
public abstract class AppDataBase extends RoomDatabase {
    public abstract CallDAO userDao();
}
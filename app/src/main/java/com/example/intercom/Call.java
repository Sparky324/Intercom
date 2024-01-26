package com.example.intercom;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity
public class Call {
    @PrimaryKey
    public int id;

    @ColumnInfo(name = "time")
    public String time;

    @ColumnInfo(name = "status")
    public String stat;
}
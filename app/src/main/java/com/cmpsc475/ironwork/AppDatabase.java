package com.cmpsc475.ironwork;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Job.class}, version = 1)

public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase INSTANCE;

    public abstract JobDao JobDao();

    private static final Object sLock = new Object();

    public static AppDatabase getInstance(Context context) {
        synchronized (sLock) {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                AppDatabase.class, "jobs.db")
                        .allowMainThreadQueries()
                        .build();
            }
            return INSTANCE;
        }
    }
}

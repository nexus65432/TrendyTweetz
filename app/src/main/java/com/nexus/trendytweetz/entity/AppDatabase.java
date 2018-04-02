
package com.nexus.trendytweetz.entity;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import com.nexus.trendytweetz.datautils.StatusesConverter;

@Database(entities = {Tweetz.class}, version = 1)
@TypeConverters({StatusesConverter.class})
public abstract class AppDatabase extends RoomDatabase {

    private static final String DB_NAME = "tweetz";

    private static AppDatabase INSTANCE;

    public abstract TweetzDao tweetzDao();

    public static AppDatabase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DB_NAME)
                    .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}
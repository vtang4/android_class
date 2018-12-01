package com.example.v.newsapp;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {NewsItem.class}, version = 1, exportSchema = false)
public abstract class NewsRoomDatabase extends RoomDatabase {

    public abstract NewsItemDao newsItemDao();

    private static volatile NewsRoomDatabase INSTANCE;

    static NewsRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (NewsRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            NewsRoomDatabase.class, "newsItem_database")
                            .build();

                }
            }
        }
        return INSTANCE;
    }

}





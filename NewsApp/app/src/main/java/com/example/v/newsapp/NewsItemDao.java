package com.example.v.newsapp;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface NewsItemDao {

    @Query("SELECT * FROM  news_item ORDER BY id ASC")
    LiveData<List<NewsItem>> loadAllNewsItems();


    @Insert
    void insert(List<NewsItem> items);

    @Delete
    void delete(NewsItem item);

    @Query("DELETE FROM  news_item")
    void clearALl();


    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateNews(NewsItem item);


}

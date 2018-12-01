package com.example.v.newsapp;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;


public class NewsItemViewModel extends AndroidViewModel {

    private NewsRepository mRepository;

    private LiveData<List<NewsItem>> mNews;

    public NewsItemViewModel( Application application) {
        super(application);
        mRepository = new NewsRepository(application);
        mNews = mRepository.loadAllNews();
    }

    public LiveData<List<NewsItem>> loadAllNewsItems() {
        return mNews;
    }

    public void insert(List<NewsItem> newsItem){
        mRepository.insert(newsItem);
    }

    public void delete(){
        mRepository.delete();
    }

    public  void update(){
        mRepository.makeNewsSearchQuery();
    }
}

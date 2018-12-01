package com.example.v.newsapp;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.Log;

import com.example.v.newsapp.JsonUtils;
import com.example.v.newsapp.NetworkUtils;
import com.example.v.newsapp.MainActivity;
import com.example.v.newsapp.NewsAdapter;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class NewsRepository {


    private NewsItemViewModel newsItemViewModel;
    NewsAdapter newsAdapter;
    private NewsItemDao mNewsItemDao;
    private LiveData<List<NewsItem>> mNews;
    List<NewsItem> newsItems;

    public NewsRepository(Application application){
      NewsRoomDatabase db = NewsRoomDatabase.getDatabase(application.getApplicationContext());
      mNewsItemDao = db.newsItemDao();
      mNews = mNewsItemDao.loadAllNewsItems();
    }

    LiveData<List<NewsItem>> loadAllNews() {
        return mNews;
    }

    public void insert(List<NewsItem> newsItem){

        new insertAsyncTask(mNewsItemDao).execute(newsItem);

    }

    public void delete(){

        new deleteAsyncTask(mNewsItemDao).execute();


    }
    private static class insertAsyncTask extends AsyncTask<List<NewsItem>, Void, Void>{
        private NewsItemDao mAsyncTaskDao;

        insertAsyncTask(NewsItemDao newsItemDao){
            mAsyncTaskDao = newsItemDao;
        }


        @Override
        protected Void doInBackground(final List<NewsItem>... newsItems) {
            for(int i = 0; i < newsItems.length; i++)
            mAsyncTaskDao.insert( newsItems[i]);
            return null;
        }


    }

    private  static class deleteAsyncTask extends AsyncTask<List<NewsItem>, Void, Void> {
        private NewsItemDao mAsyncTaskDao;

        deleteAsyncTask(NewsItemDao newsItemDao) {
            mAsyncTaskDao = newsItemDao;
        }


        @Override
        protected Void doInBackground(final List<NewsItem>... newsItems) {

            mAsyncTaskDao.clearALl();
            return null;

        }
    }

        public void makeNewsSearchQuery(){
            URL url = NetworkUtils.buildUrl();
            new NewsQueryTask().execute(url);
        }

        public class NewsQueryTask extends AsyncTask<URL, Void, String>{

            @Override
            protected  void onPreExecute(){
                super.onPreExecute();
                delete();
            }


            @Override
            protected String doInBackground(URL... urls) {
                URL url = urls[0];
                String searchResult = null;
                try{
                    searchResult = NetworkUtils.getResponseFromHttpUrl(url);

                }catch (IOException e){
                    e.printStackTrace();
                }

                return searchResult;

            }

        @Override
            protected  void onPostExecute(String searchResult){
                super.onPostExecute(searchResult);
                newsItems = JsonUtils.parseNews(searchResult);
                insert(newsItems);
        }




        }

    }



package com.example.v.newsapp;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String>  {


    private static final String TAG = "MainActivity";
    private static final String SEARCH_QUERY_URL_EXTRA = "searchQuery";
    private static final String SEARCH_QUERY_RESULTS = "searchResults";
private String newsSearchResults;
    public RecyclerView mRecyclerView;
    public NewsAdapter mAdapter;
    private static final int LOADER_ID = 1;
    private ArrayList<NewsItem> newsItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.news_recyclerview);
        mAdapter = new NewsAdapter(this, newsItems);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        if(savedInstanceState != null && savedInstanceState.containsKey(SEARCH_QUERY_RESULTS)){
            String searchResults = savedInstanceState.getString(SEARCH_QUERY_RESULTS);
            populateRecyclerView(searchResults);

        }


    }

//    @Override
//    protected  void onSaveInstanceState(Bundle outState){
//        super.onSaveInstanceState(outState);
//        outState.putString(SEARCH_QUERY_RESULTS, searchResults );
//
//    }

    private URL makeSearchUrl(){

        URL searchUrl = NetworkUtils.buildUrl("");
        return searchUrl;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemThatWasClickedId = item.getItemId();
        if (itemThatWasClickedId == R.id.action_search) {
            URL url = makeSearchUrl();
            Bundle bundle = new Bundle();
            bundle.putString(SEARCH_QUERY_URL_EXTRA, url.toString());
            LoaderManager loaderManager = getSupportLoaderManager();
            Loader<String> newsSearchLoader = loaderManager.getLoader(LOADER_ID);
            if(newsSearchLoader == null){
                loaderManager.initLoader(LOADER_ID, bundle,  this).forceLoad();
            }else{
                loaderManager.restartLoader(LOADER_ID, bundle, this).forceLoad();
            }
            return true;
        }


        return super.onOptionsItemSelected(item);
    }






    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.get_news, menu);
        return true;
    }

    public void populateRecyclerView(String searchResults){
        Log.d("mycode", searchResults);
        newsItems = JsonUtils.parseNews(searchResults);
        mAdapter.mNewsItems.addAll(newsItems);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public android.support.v4.content.Loader<String> onCreateLoader(int id, final Bundle args) {
        return new AsyncTaskLoader<String>(this) {
            @Override
            protected void onStartLoading() {
                Log.d(TAG, "onStartLoading called");
                super.onStartLoading();
                if(args == null){
                    Log.d(TAG, "bundle null");
                    return;
                }

            }

            @Override
            public String loadInBackground() {
                Log.d(TAG, "loadInBackground called");

                String newsSearch = args.getString(SEARCH_QUERY_URL_EXTRA);
                if(newsSearch == null || newsSearch.isEmpty()){
                    return null;
                }
                try {
                    Log.d(TAG, "begin network call");
                    newsSearchResults = NetworkUtils.getResponseFromHttpUrl(new URL(newsSearch));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.d(TAG, newsSearchResults);
                return newsSearchResults;
            }
        };
    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader<String> loader, String data) {
        Log.d("mycode", data);

        populateRecyclerView(data);
    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<String> loader) {}
}




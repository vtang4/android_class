package com.example.v.newsapp;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import java.util.List;

public class MainActivity extends AppCompatActivity implements NewsAdapter.itemClickListener {


     NewsItemViewModel newsItemViewModel;
 //    NewsRepository newsRepository;
     RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.news_recyclerview);
        newsItemViewModel = ViewModelProviders.of(this).get(NewsItemViewModel.class);
        final NewsAdapter adapter = new NewsAdapter(MainActivity.this , newsItemViewModel);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        newsItemViewModel.loadAllNewsItems().observe(this, new Observer<List<NewsItem>>() {
            @Override
            public void onChanged(@Nullable List<NewsItem> newsItems) {
                adapter.setNewsItems(newsItems);
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        UpdateUtils.scheduleUpdate(this);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemThatWasClickedId = item.getItemId();
        if (itemThatWasClickedId == R.id.action_search) {
          newsItemViewModel.update();
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.get_news, menu);
        return true;
    }


    @Override
    public void onItemClick(int i){
        Uri uri = Uri.parse(newsItemViewModel.loadAllNewsItems().getValue().get(i).getUrl());
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);

        startActivity(intent);

    }






}




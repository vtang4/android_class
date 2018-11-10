package com.example.v.newsapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebView;

public class WebActivity extends AppCompatActivity {

    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_main);

        webView = (WebView) findViewById(R.id.webview);


    }

    @Override
    protected void onResume(){
        super.onResume();
        Intent intent = getIntent();
        String url = "";
        if(intent.hasExtra("url")){
            url = intent.getStringExtra("url");
            Log.d("mycode", url);

        }
        webView.loadUrl(url);

    }




}

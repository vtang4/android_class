package com.example.v.newsapp;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

public class NewsUpdateIntentService extends IntentService{

    public NewsUpdateIntentService() {
        super("NewUpdateIntentService");
    }
    @Override
    protected void onHandleIntent(Intent intent) {
        String action = intent.getAction();
        UpdateTask.executeTask(this,action);
    }
}

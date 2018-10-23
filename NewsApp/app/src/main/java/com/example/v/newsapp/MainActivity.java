package com.example.v.newsapp;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String>{


    private static final String TAG = "MainActivity";
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public android.support.v4.content.Loader<String> onCreateLoader(int id, final Bundle args){
        return new AsyncTaskLoader<String>(context: this) {

            @Override
            protected  void onStartLoading(){
                Log.d(TAG , msg:"onStartLoading called");
                super.onStartLoading();
                if(args == null){
                    Log.d(TAG, msg: "bundle null");
                    return;
                }

                mProgressBar.setVisibility(View.VISIBLE);

            }



            @Override
            public String loadInBackground() {
                return null;
            }
        }

    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String s) {

    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }


}


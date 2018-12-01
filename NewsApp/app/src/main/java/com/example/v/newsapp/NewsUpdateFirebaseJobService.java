package com.example.v.newsapp;

import android.annotation.TargetApi;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class NewsUpdateFirebaseJobService extends JobService {

    private AsyncTask mBackgroundTask;
    NewsRepository newsRepository;


    @Override
    public boolean onStartJob(final JobParameters params) {

        mBackgroundTask = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                Context context = NewsUpdateFirebaseJobService.this;
                UpdateTask.executeTask(context, UpdateTask.UPDATE_NEWS);
                newsRepository = new NewsRepository(getApplication());
                newsRepository.makeNewsSearchQuery();

                return null;
            }
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            protected void onPostExecute(Object o){

                jobFinished(params, false);
            }
        };

        mBackgroundTask.execute();

        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        if(mBackgroundTask != null)
            mBackgroundTask.cancel(true);
        return true;
    }
}

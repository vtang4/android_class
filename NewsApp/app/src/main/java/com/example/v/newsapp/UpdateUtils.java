package com.example.v.newsapp;

import android.content.Context;
import android.support.annotation.NonNull;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.Driver;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.Trigger;

import java.util.concurrent.TimeUnit;

public class UpdateUtils {

//    private static final int UPDATE_INTERVAL_MINUTES = 1;
    private static final int UPDATE_INTERVAL_SECONDS = 10;
    private static final int SYNC_FLEXTIME_SECONDS = UPDATE_INTERVAL_SECONDS;

    private static final String UPDATE_JOB_TAG = "news_update_tag";

    private static boolean sInitialized;

    synchronized public static void scheduleUpdate(@NonNull final Context context) {

        if (sInitialized) return;

        Driver driver = new GooglePlayDriver(context);
        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(driver);

        Job constraintUpdateJob = dispatcher.newJobBuilder()
                .setService(NewsUpdateFirebaseJobService.class)
                .setTag(UPDATE_JOB_TAG)
                .setConstraints(Constraint.ON_UNMETERED_NETWORK)
                .setLifetime(Lifetime.FOREVER)
                .setRecurring(true)
                .setTrigger(Trigger.executionWindow(
                        //0,10))
                       UPDATE_INTERVAL_SECONDS,
                       UPDATE_INTERVAL_SECONDS + SYNC_FLEXTIME_SECONDS))
                .setReplaceCurrent(true)
                .build();

        dispatcher.schedule(constraintUpdateJob);
        sInitialized = true;

    }
}

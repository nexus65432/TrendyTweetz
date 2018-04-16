package com.nexus.trendytweetz.network;

import android.annotation.TargetApi;
import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


public class TweetScheduler extends JobService {

    private static final String TAG = "TweetScheduler";

    private static final int JOB_SCHEDULER = 35468;

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        Log.d(TAG, "onStartJob ");
        scheduleJob(getApplicationContext());
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        Log.d(TAG, "onStopJob ");
        return true;
    }

    // schedule the start of the service every 10 - 30 seconds
    @TargetApi(23)
    public static void scheduleJob(Context context) {
        ComponentName serviceComponent = new ComponentName(context, TweetScheduler.class);
        JobInfo.Builder builder = new JobInfo.Builder(JOB_SCHEDULER, serviceComponent);
        builder.setMinimumLatency(1 * 1000); // wait at least
        builder.setOverrideDeadline(3 * 1000); // maximum delay
        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED); // require unmetered network
        builder.setRequiresDeviceIdle(false); // device should be idle
        builder.setRequiresCharging(false); // we don't care if the device is charging or not
        JobScheduler jobScheduler = context.getSystemService(JobScheduler.class);
        jobScheduler.schedule(builder.build());
    }
}

package com.nexus.trendytweetz.listeners;


import android.annotation.TargetApi;
import android.content.Context;

/**
 * This class provides interface so that user can choose the type of scheduling he wants
 * either RxJava or Android Job Scheduler or Async Taks (old way :()
 */
public interface Scheduler {

    void schedulePolling(Context context, String hashTag);

    /**
     * Schedule polling for new tweets for the given hashTag using RxJava2
     * @param hashTag
     */
    void schedulePollingForNewTweetsWithRxJava(String hashTag);

    /**
     * Schedule polling for new tweets for the given hashTag using JobScheduler
     * @param hashTag
     */
    @TargetApi(23)
    void schedulePollingForNewTweetsWithJobScheduler(Context context, String hashTag);
}

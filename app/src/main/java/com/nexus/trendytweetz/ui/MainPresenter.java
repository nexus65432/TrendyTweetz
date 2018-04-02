package com.nexus.trendytweetz.ui;

import android.content.Context;
import android.support.annotation.NonNull;

import com.nexus.trendytweetz.datamodel.StatusesItem;

import java.util.List;

public interface MainPresenter {

    /**
     * Attach to the View, this will bind a communication between UI
     * @param context
     */
    void onAttach(@NonNull Context context);

    /**
     * Get tweets for the given input hashTag from server
     * @param hashTag
     */
    void getTweetsWithHashTagFromServer(@NonNull String hashTag);

    /**
     * Schedule polling for new tweets for the given hashTag
     * @param hashTag
     */
    void schedulePollingForNewTweets(String hashTag);

    /**
     * Sort new tweets based on the current list latest timestamp
     * @param latestTimeStamp
     * @param newList   list from adapter
     */
    void processNewTweets(@NonNull final long latestTimeStamp, @NonNull List<StatusesItem> newList);

    /**
     * Prepare to exit the UI, stop any schedulers and cleanup any other views
     */
    void prepareToExit();

    /**
     * Stop requesting for new tweets
     */
    void stopPollingScheduler();

    /**
     * Reset any values and make room for GC to cleanup any used resources
     */
    void onDetach();

}

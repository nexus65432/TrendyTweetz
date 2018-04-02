package com.nexus.trendytweetz.ui;

import android.support.annotation.NonNull;

import com.nexus.trendytweetz.datamodel.StatusesItem;
import com.nexus.trendytweetz.listeners.NetworkStatusListener;
import com.nexus.trendytweetz.listeners.SearchListener;
import com.nexus.trendytweetz.viewmodel.TweetViewModelListener;

import java.util.List;

/**
 * This interface will be extended by a more specific interface
 * that then usually will be implemented by an Activity or Fragment.
 */
public interface MainView extends SearchListener, NetworkStatusListener, TweetViewModelListener {

    /**
     * Add tweets to the adapter
     * @param results
     */
    void addTweetsToList(@NonNull List<StatusesItem> results);

    /**
     * Replace existing list with new list to the adapter
     * @param results
     */
    void replaceTweetsWithNewInList(@NonNull List<StatusesItem> results);

    /**
     * Add single tweet to the top of the list
     * @param results
     */
    void addNewTweetToList(@NonNull StatusesItem results);

    /**
     * Clear the list and present user with right full information
     */
    void showEmptyList();

    /**
     * Prepare for showing new lists when user request with new search
     */
    void prepareForNewList();

    /**
     * Hide keyboard after user action
     */
    void hideKeyboard();
}

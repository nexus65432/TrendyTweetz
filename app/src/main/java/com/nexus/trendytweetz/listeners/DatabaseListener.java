package com.nexus.trendytweetz.listeners;

import android.support.annotation.NonNull;

import com.nexus.trendytweetz.entity.Tweetz;

/**
 * Use this interface to implement the API's related to database
 */
public interface DatabaseListener {

    /**
     * Get List of Tweetz from the current table for the given searchKey
     * @param searchKey
     */
    void loadTweetsFromDatabase(@NonNull String searchKey);

    /**
     * Get List of Tweetz from the current table for the given searchKey, while the user is offline.
     * @param searchKey
     */
    void getTweetsFromTableWithSearchKey(@NonNull String searchKey);

    /**
     * Add Tweetz object to the table.
     * @param tweetz
     */
    void addTweetsToDatabase(@NonNull Tweetz tweetz);
}

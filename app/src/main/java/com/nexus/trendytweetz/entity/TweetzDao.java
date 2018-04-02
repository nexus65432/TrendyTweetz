package com.nexus.trendytweetz.entity;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.support.annotation.NonNull;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;


/**
 * Data Access Object for the tweetz table.
 */
@Dao
public interface TweetzDao {

    /**
     * Get all tweets from the table.
     * @return Tweetz list
     */
    @Query("SELECT * FROM tweetz")
    List<Tweetz> getAllTweets();

    /**
     * Get the Tweetz from the table for the searchKey
     * @return Tweetz list from the table
     */
    @Query("SELECT * FROM tweetz WHERE searchkey == :queryKey")
    List<Tweetz> getTweetsBySearchKey(@NonNull String queryKey);

    /**
     * Insert a tweet into table. If the tweet already exists, replace it.
     * @param tweetz the user to be inserted.
     */
    @Insert(onConflict = REPLACE)
    void insertTweetz(@NonNull Tweetz tweetz);
}

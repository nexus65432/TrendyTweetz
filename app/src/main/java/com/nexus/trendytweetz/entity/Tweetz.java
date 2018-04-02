package com.nexus.trendytweetz.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.nexus.trendytweetz.datautils.StatusesConverter;
import com.nexus.trendytweetz.datamodel.StatusesItem;

import java.util.List;

@Entity
public class Tweetz {

    @PrimaryKey(autoGenerate = true)
    private int uid;

    @ColumnInfo(name = "searchKey")
    String searchKey;

    @ColumnInfo(name = "timestamp")
    long tweetTimeStamp;

    @ColumnInfo(name = "tweets")
    @TypeConverters(StatusesConverter.class)
    List<StatusesItem> tweets;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }

    public long getTweetTimeStamp() {
        return tweetTimeStamp;
    }

    public void setTweetTimeStamp(long tweetTimeStamp) {
        this.tweetTimeStamp = tweetTimeStamp;
    }

    public List<StatusesItem> getTweets() {
        return tweets;
    }

    public void setTweets(List<StatusesItem> tweets) {
        this.tweets = tweets;
    }
}

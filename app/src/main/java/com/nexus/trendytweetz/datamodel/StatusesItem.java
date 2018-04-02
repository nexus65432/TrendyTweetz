package com.nexus.trendytweetz.datamodel;

import android.arch.persistence.room.TypeConverters;

import com.google.gson.annotations.SerializedName;
import com.nexus.trendytweetz.datautils.EntityObjConverter;
import com.nexus.trendytweetz.datautils.UserObjConverter;
import com.nexus.trendytweetz.entity.User;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Mapping server data to local class. Presently using only limited data to display in UI
 */
public class StatusesItem {

    @SerializedName("id")
    String tweetid;

    @SerializedName("created_at")
    String tweet_creation_time;

    @SerializedName("text")
    String tweettext;

    @TypeConverters(UserObjConverter.class)
    @SerializedName("user")
    User user;

    @TypeConverters(EntityObjConverter.class)
    @SerializedName("entities")
    Entities entities;

    public String getTweet_creation_time() {
        return tweet_creation_time;
    }

    public long getTweetCreationTime() {
        Date tweet_date_time = null;
        DateFormat fromFormat = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");
        try {
            tweet_date_time = fromFormat.parse(getTweet_creation_time());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return tweet_date_time.getTime();
    }

    public String getTweetid() {
        return tweetid;
    }

    public String getTweettext() {
        return tweettext;
    }

    @TypeConverters(UserObjConverter.class)
    public User getUser() {
        return user;
    }

    public Entities getEntities() {
        return entities;
    }

    public void setEntities(Entities entities) {
        this.entities = entities;
    }
}

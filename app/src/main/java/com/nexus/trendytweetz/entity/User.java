package com.nexus.trendytweetz.entity;

import android.arch.persistence.room.Entity;

import com.google.gson.annotations.SerializedName;

/**
 * User equivalent object of server response
 */
@Entity
public class User {

    @SerializedName("name")
    String user_name;

    @SerializedName("screen_name")
    String user_screen_name;

    @SerializedName("profile_image_url")
    String user_profile_image_url;

    @SerializedName("profile_image_url_https")
    String user_profile_image_url_https;

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_screen_name() {
        return user_screen_name;
    }

    public void setUser_screen_name(String user_screen_name) {
        this.user_screen_name = user_screen_name;
    }

    public String getUser_profile_image_url() {
        return user_profile_image_url;
    }

    public void setUser_profile_image_url(String user_profile_image_url) {
        this.user_profile_image_url = user_profile_image_url;
    }

    public String getUser_profile_image_url_https() {
        return user_profile_image_url_https;
    }

    public void setUser_profile_image_url_https(String user_profile_image_url_https) {
        this.user_profile_image_url_https = user_profile_image_url_https;
    }
}

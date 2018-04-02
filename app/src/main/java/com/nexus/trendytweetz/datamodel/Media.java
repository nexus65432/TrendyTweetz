package com.nexus.trendytweetz.datamodel;


import com.google.gson.annotations.SerializedName;


public class Media {

    @SerializedName("id")
    String id;

    @SerializedName("media_url")
    String media_url;

    @SerializedName("media_url_https")
    String media_url_https;

    @SerializedName("display_url")
    String display_url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMedia_url() {
        return media_url;
    }

    public void setMedia_url(String media_url) {
        this.media_url = media_url;
    }

    public String getMedia_url_https() {
        return media_url_https;
    }

    public void setMedia_url_https(String media_url_https) {
        this.media_url_https = media_url_https;
    }

    public String getDisplay_url() {
        return display_url;
    }

    public void setDisplay_url(String display_url) {
        this.display_url = display_url;
    }
}

package com.nexus.trendytweetz.datamodel;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Entities {

    @SerializedName("media")
    List<Media> media;

    public List<Media> getMedia() {
        return media;
    }

    public void setMedia(List<Media> media) {
        this.media = media;
    }
}

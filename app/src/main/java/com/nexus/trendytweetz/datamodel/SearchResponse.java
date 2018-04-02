package com.nexus.trendytweetz.datamodel;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchResponse {

    @SerializedName("statuses")
    List<StatusesItem>  statusesItems;

    @SerializedName("search_metadata")
    SearchMetadata metadata;

    public List<StatusesItem> getStatusesItems() {
        return statusesItems;
    }

    public SearchMetadata getMetadata() {
        return metadata;
    }
}

package com.nexus.trendytweetz.datamodel;

import com.google.gson.annotations.SerializedName;

/**
 * Mapping server data to local class
 */
public class SearchMetadata {

    @SerializedName("query")
    String searchQuery;

    public String getSearchQuery() {
        return searchQuery;
    }
}

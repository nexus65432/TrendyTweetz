package com.nexus.trendytweetz.network;

import com.nexus.trendytweetz.datamodel.SearchResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Class to build Twitter API request
 * Twitter provides information about tweets for a validity of 1 week.
 */
public interface TwitterApi {

    @GET("search/tweets.json")
    Single<SearchResponse> searchTweets(@Query("q") String query);
}

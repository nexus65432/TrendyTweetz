package com.nexus.trendytweetz.network;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.nexus.trendytweetz.BuildConfig;
import com.nexus.trendytweetz.datamodel.SearchResponse;

import io.reactivex.Single;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Rest interface to fetch data from server
 */
public class RetrofitService {

    private Retrofit mRetroFit;

    private TwitterApi mTwitterApi;

    private static class SingletonRetroFitServiceHelper {
        private static final RetrofitService INSTANCE = new RetrofitService();
    }

    public static RetrofitService getInstance() {
        return SingletonRetroFitServiceHelper.INSTANCE;
    }

    private RetrofitService() {
        final Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
        mRetroFit = new Retrofit.Builder().baseUrl(BuildConfig.END_POINT)
                .client(getOkHttpClient())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    private OkHttpClient getOkHttpClient() {
        return new OkHttpClient.Builder().addInterceptor(new RetrofitInterceptor()).build();
    }

    public Single<SearchResponse> getSearchResults(String inputTag) {
        mTwitterApi = mRetroFit.create(TwitterApi.class);
        return mTwitterApi.searchTweets(inputTag);
    }
}

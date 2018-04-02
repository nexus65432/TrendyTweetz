package com.nexus.trendytweetz.listeners;

import android.support.annotation.StringRes;


public interface NetworkStatusListener {

    void loading(@StringRes int resource);

    void onSuccess();

    void onError();

    void onError(@StringRes int resource);
}

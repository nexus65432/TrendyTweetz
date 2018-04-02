package com.nexus.trendytweetz.viewmodel;

/**
 * Interface to subscribe or unsubscribe on the TweetViewModel
 */
public interface TweetViewModelListener {

    /**
     * Listen to the View Model
     */
    void subscribeToTweetViewModel();

    /**
     * Unregister to the View Model
     */
    void UnSubscribeFromTweetViewModel();
}

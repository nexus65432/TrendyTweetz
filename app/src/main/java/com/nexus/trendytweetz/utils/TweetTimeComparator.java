package com.nexus.trendytweetz.utils;

import com.nexus.trendytweetz.datamodel.StatusesItem;

import java.util.Comparator;

/**
 * Comparator used to sort tweets based on Time
 */
public class TweetTimeComparator implements Comparator<StatusesItem> {

    @Override
    public int compare(StatusesItem tweet1, StatusesItem tweet2) {
        return Long.compare(tweet1.getTweetCreationTime(), tweet2.getTweetCreationTime());
    }
}
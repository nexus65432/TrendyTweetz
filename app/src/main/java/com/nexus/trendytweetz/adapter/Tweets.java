package com.nexus.trendytweetz.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nexus.trendytweetz.R;

/**
 *  A ViewHolder object stores each of the component views inside the tag field of the Layout,
 *  so you can immediately access them without the need to look them up repeatedly
 */
public class Tweets extends RecyclerView.ViewHolder {

    public ImageView mTweetUserPic;
    public TextView mTweetUserName;
    public TextView mTweetMessage;
    public ImageView mTweetSharedImage;

    public Tweets(View itemView) {
        super(itemView);

        mTweetUserPic = itemView.findViewById(R.id.user_avatar);
        mTweetUserName = itemView.findViewById(R.id.tweet_username);
        mTweetMessage = itemView.findViewById(R.id.tweet_message);
        mTweetSharedImage = itemView.findViewById(R.id.tweet_image);
    }
}

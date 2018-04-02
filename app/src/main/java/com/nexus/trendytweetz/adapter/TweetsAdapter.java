package com.nexus.trendytweetz.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.nexus.trendytweetz.datamodel.StatusesItem;
import com.nexus.trendytweetz.R;
import com.nexus.trendytweetz.utils.DateUtils;
import com.nexus.trendytweetz.utils.GlideCircleTransformation;

import java.util.List;

/**
 * Adapter providing a binding Tweets data set to views that are displayed in List
 */
public class TweetsAdapter extends RecyclerView.Adapter<Tweets> {

    private Context mContext;
    private List<StatusesItem> mTweetItems;

    public TweetsAdapter(@NonNull Context context, @NonNull List<StatusesItem> tweets) {
        mContext = context;
        mTweetItems = tweets;
    }

    @Override
    public Tweets onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext)
                .inflate(R.layout.tweet_layout_item, parent, false);

        return new Tweets(itemView);
    }

    @Override
    public void onBindViewHolder(Tweets holder, int position) {

        final StatusesItem tweet = mTweetItems.get(position);

        if (tweet != null) {

            if (tweet.getUser() != null) {
                final String cardImageUrl = tweet.getUser().getUser_profile_image_url();
                if (cardImageUrl != null) {
                    Glide.with(mContext)
                            .load(cardImageUrl)
                            .centerCrop()
                            .bitmapTransform(new GlideCircleTransformation(mContext))
                            .placeholder(R.drawable.user_icon)
                            .error(R.drawable.user_icon)
                            .into(holder.mTweetUserPic);
                }

                final String userDisplayName = tweet.getUser().getUser_screen_name();
                final String timeStamp = DateUtils.getDurationString(tweet.getTweetCreationTime());
                if (!TextUtils.isEmpty(userDisplayName)) {
                    StringBuilder header = new StringBuilder().append(userDisplayName).append(" ").append(timeStamp);
                    holder.mTweetUserName.setText(header.toString());
                }
            }

            final String tweetMessage = tweet.getTweettext();
            if (!TextUtils.isEmpty(tweetMessage)) {
                holder.mTweetMessage.setText(tweetMessage);
            }

            if (tweet.getEntities() != null &&
                    tweet.getEntities().getMedia() != null &&
                    tweet.getEntities().getMedia().size() > 0) {
                final String tweetImageUrl = tweet.getEntities().getMedia().get(0).getMedia_url();
                if (tweetImageUrl != null) {
                    holder.mTweetSharedImage.setVisibility(View.VISIBLE);
                    Glide.with(mContext)
                            .load(tweetImageUrl)
                            .centerCrop()
                            .error(R.drawable.user_icon)
                            .into(holder.mTweetSharedImage);
                }
            } else {
                holder.mTweetSharedImage.setVisibility(View.GONE);
            }
        }
    }

    public void addList(List<StatusesItem> statusesItems) {
        mTweetItems = statusesItems;
    }

    public void replaceWithNewList(List<StatusesItem> statusesItems) {
        mTweetItems = statusesItems;
    }


    public void addNewTweet(@NonNull StatusesItem tweet) {
        mTweetItems.add(0, tweet);
    }

    public List<StatusesItem> getTweetItems() {
        return mTweetItems;
    }

    @Override
    public int getItemCount() {
        int retVal = 0;
        if (mTweetItems != null) {
            retVal = mTweetItems.size();
        }
        return retVal;
    }
}

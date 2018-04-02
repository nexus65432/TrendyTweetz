package com.nexus.trendytweetz.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.nexus.trendytweetz.datamodel.SearchResponse;
import com.nexus.trendytweetz.datamodel.StatusesItem;
import com.nexus.trendytweetz.network.RetrofitService;
import com.nexus.trendytweetz.R;
import com.nexus.trendytweetz.utils.TweetTimeComparator;
import com.nexus.trendytweetz.entity.AppDatabase;
import com.nexus.trendytweetz.entity.Tweetz;
import com.nexus.trendytweetz.listeners.DatabaseListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.observers.ResourceObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.schedulers.Timed;

/**
 * This class implements the logic which are needed for the UI
 * It implements the type of functionalities it provides
 * MainPresenter - Basic functionality of this class
 * DatabaseListener - Operations w.r.t database.
 */
public class MainPresenterImpl implements MainPresenter, DatabaseListener {

    private static final String TAG = "MainPresenterImpl";

    // Setting the listener to 5 seconds for quick ui updates. This can be configured
    private static final int TWEET_INTERVAL = 5;

    private AppDatabase mAppDatabase;

    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private MainView mMainView;

    private ResourceObserver<List<StatusesItem>> mObservable;
    private Disposable mPollingObservable;

    public MainPresenterImpl(MainView view) {
        this.mMainView = view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        mAppDatabase = AppDatabase.getAppDatabase(context);
    }

    @Override
    public void prepareToExit() {
        Log.d(TAG, "prepareToExit ");
        stopPollingScheduler();
    }

    @Override
    public void onDetach() {
        Log.d(TAG, "onDetach ");
        if (mObservable != null && !mObservable.isDisposed()) {
            mObservable.dispose();
        }
        if (mPollingObservable != null && !mPollingObservable.isDisposed()) {
            mPollingObservable.dispose();
        }

        AppDatabase.destroyInstance();
    }

    @Override
    public void stopPollingScheduler() {
        Log.d(TAG, "stopPollingScheduler ");
        if (mPollingObservable != null && !mPollingObservable.isDisposed()) {
            mPollingObservable.dispose();
        }
    }

    @Override
    public void getTweetsFromTableWithSearchKey(@NonNull final String input) {
        Log.d(TAG, "getTweetsFromTableWithSearchKey " + input);

        mObservable = Observable.just(input)
                .subscribeOn(Schedulers.computation())
                .map(new Function<String, List<StatusesItem>>() {
                    @Override
                    public List<StatusesItem> apply(String newTweets) {

                        if (mAppDatabase.tweetzDao().getTweetsBySearchKey(input) != null) {
                            List<Tweetz> tweetsList = mAppDatabase.tweetzDao().getTweetsBySearchKey(input);

                            if (tweetsList != null) {
                                Log.d(TAG, "getTweetsFromTableWithSearchKey size " + tweetsList.size());

                                List<StatusesItem> tweetItems = new ArrayList<>();
                                for (Tweetz item : tweetsList) {
                                    tweetItems.addAll(item.getTweets());
                                }
                                Collections.sort(tweetItems, new TweetTimeComparator());
                                return tweetItems;
                            }
                        }
                        return null;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new ResourceObserver<List<StatusesItem>>() {
                    @Override
                    public void onNext(List<StatusesItem> tweetsList) {
                        Log.d(TAG, "getTweetsFromTableWithSearchKey onNext size " + tweetsList.size());
                        if (tweetsList != null && tweetsList.size() > 0) {
                            mMainView.onSuccess();
                            mMainView.replaceTweetsWithNewInList(tweetsList);
                        } else {
                            mMainView.showEmptyList();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "getTweetsFromTableWithSearchKey onError ");
                        mMainView.showEmptyList();
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "getTweetsFromTableWithSearchKey onComplete ");
                    }
                });
    }

    @Override
    public void loadTweetsFromDatabase(@NonNull final String input) {
        Log.d(TAG, "loadTweetsFromDatabase " + input);

        mObservable = Observable.just(input)
                .subscribeOn(Schedulers.computation())
                .map(new Function<String, List<StatusesItem>>() {
                    @Override
                    public List<StatusesItem> apply(String newTweets) {

                        if (mAppDatabase.tweetzDao().getTweetsBySearchKey(input) != null) {
                            List<Tweetz> tweetsList = mAppDatabase.tweetzDao().getTweetsBySearchKey(input);

                            if (tweetsList != null) {
                                Log.d(TAG, "loadTweetsFromDatabase size " + tweetsList.size());
                            }

                            int tweetzCount = tweetsList.size();
                            if (tweetzCount > 0) {
                                List<StatusesItem> tweetItems = new ArrayList<>();
                                tweetItems.addAll(tweetsList.get(tweetzCount - 1).getTweets());
                                return tweetItems;
                            }
                        }
                        return null;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new ResourceObserver<List<StatusesItem>>() {
                    @Override
                    public void onNext(List<StatusesItem> tweetsList) {
                        Log.d(TAG, "loadTweetsFromDatabase onNext size " + tweetsList.size());
                        if (tweetsList != null && tweetsList.size() > 0) {
                            mMainView.onSuccess();
                            mMainView.addTweetsToList(tweetsList);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "loadTweetsFromDatabase onError ");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "loadTweetsFromDatabase onComplete ");
                    }
                });
    }

    @Override
    public void addTweetsToDatabase(@NonNull final Tweetz tweetz) {
        Log.d(TAG, "addTweetsToDatabase ");

        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                mAppDatabase.tweetzDao().insertTweetz(tweetz);
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "onSubscribe ");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete ");
                        loadTweetsFromDatabase(tweetz.getSearchKey());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError ");
                    }
                });
    }

    @Override
    public void schedulePollingForNewTweets(final String hashTag) {
        Log.d(TAG, "schedulePollingForNewTweets ");
        if (TextUtils.isEmpty(hashTag)) {
            return;
        }
        mPollingObservable = Observable.interval(TWEET_INTERVAL, TimeUnit.SECONDS).timeInterval()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Timed<Long>>() {
                    @Override
                    public void accept(Timed<Long> longTimed) throws Exception {
                        Log.d(TAG, "Poll For New Tweets " + hashTag);
                        getTweetsWithHashTagFromServer(hashTag);
                    }
                });
    }

    @Override
    public void getTweetsWithHashTagFromServer(@NonNull String hashTag) {
        Log.d(TAG, "getTweetsWithHashTagFromServer " + hashTag);
        mMainView.loading(R.string.loading_tweets);
        mCompositeDisposable.add(RetrofitService.getInstance().getSearchResults(hashTag)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(getLatestTweetsObserver()));
    }

    private DisposableSingleObserver<SearchResponse> getLatestTweetsObserver() {
        return new DisposableSingleObserver<SearchResponse>() {
            @Override
            public void onSuccess(SearchResponse value) {
                Log.d(TAG, "getLatestTweetsObserver onSuccess ");

                Tweetz tweetz = new Tweetz();
                tweetz.setTweets(value.getStatusesItems());
                tweetz.setSearchKey(value.getMetadata().getSearchQuery());
                tweetz.setTweetTimeStamp(value.getStatusesItems().get(0).getTweetCreationTime());
                addTweetsToDatabase(tweetz);
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "getLatestTweetsObserver onError ");
                mMainView.onError(R.string.error_message);
            }
        };
    }

    @Override
    public void processNewTweets(@NonNull final long latestTimeStamp, @NonNull List<StatusesItem> newTweetsList) {
        mObservable = Observable.just(newTweetsList)
                .subscribeOn(Schedulers.computation())
                .map(new Function<List<StatusesItem>, List<StatusesItem>>() {
                    @Override
                    public List<StatusesItem> apply(List<StatusesItem> newTweets) {

                        List<StatusesItem> statusesItems = new ArrayList<>();

                        for (StatusesItem item : newTweets) {
                            if (item.getTweetCreationTime() > latestTimeStamp) {
                                statusesItems.add(item);
                            }
                        }

                        Collections.sort(statusesItems, new TweetTimeComparator());
                        //runs in a secondary thread
                        return statusesItems;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new ResourceObserver<List<StatusesItem>>() {
                    @Override
                    public void onNext(List<StatusesItem> newOrderedTweets) {
                        Log.d(TAG, "update new tweets onNext size " + newOrderedTweets.size());
                        if (newOrderedTweets.size() > 0) {
                            /*
                            for (StatusesItem item : newOrderedTweets) {
                                mMainView.addNewTweetToList(item);
                            }
                            */
                            // Adding only one Item to the list for demo.
                            mMainView.addNewTweetToList(newOrderedTweets.get(0));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "processNewTweets onError ");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "processNewTweets onComplete ");
                    }
                });
    }
}

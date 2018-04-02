package com.nexus.trendytweetz.ui;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.nexus.trendytweetz.adapter.TweetsAdapter;
import com.nexus.trendytweetz.datautils.SearchSuggestionProvider;
import com.nexus.trendytweetz.datamodel.StatusesItem;
import com.nexus.trendytweetz.R;
import com.nexus.trendytweetz.utils.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements MainView, SearchView.OnQueryTextListener {

    private static final String TAG = "MainActivity";

    private static final String DEFAULT_SEARCH_HASHTAG = "facebook";

    private Toolbar mToolbar;
    private TextView mToolbarTitle;
    private RecyclerView mTweetsList;
    private TextView mDefaultMessage;
    private MenuItem mSearchViewItem;
    private SearchView mSearchView;
    private TweetsAdapter mTweetsAdapter;
    private List<StatusesItem> mTweetItems = new ArrayList<>();

    private MainPresenterImpl mMainPresenterImpl;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDefaultMessage = findViewById(R.id.error_message);
        setupToolbar();
        setUpRecyclerView();

        mMainPresenterImpl = new MainPresenterImpl(this);
        mMainPresenterImpl.onAttach(MainActivity.this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart ");
        //subscribeToTweetViewModel();
        // Load data with default search key
        onQueryTextSubmit(DEFAULT_SEARCH_HASHTAG);
    }

    /**
     * This is called when user selects data from history
     *
     * @param intent
     */
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.d(TAG, "onNewIntent ");
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this,
                    SearchSuggestionProvider.AUTHORITY, SearchSuggestionProvider.MODE);
            suggestions.saveRecentQuery(query, null);
            mSearchView.setQuery(query, true);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop ");
        //clearQueryHistory();
        mMainPresenterImpl.prepareToExit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy ");
        //UnSubscribeFromTweetViewModel();
        mMainPresenterImpl.onDetach();
    }

    private void setupToolbar() {
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mToolbarTitle = findViewById(R.id.toolbar_title);
        mToolbarTitle.setText(getString(R.string.actionbar_title));
    }

    /**
     * Setup recyclerView
     */
    private void setUpRecyclerView() {
        mTweetsAdapter = new TweetsAdapter(this, mTweetItems);
        mTweetsList = findViewById(R.id.listView);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mTweetsList.getContext(),
                mLayoutManager.getOrientation());

        mTweetsList.setItemAnimator(new DefaultItemAnimator());
        mTweetsList.setLayoutManager(mLayoutManager);
        mTweetsList.addItemDecoration(dividerItemDecoration);
        mTweetsList.setAdapter(mTweetsAdapter);
    }

    private void updateMessage(boolean show) {
        Log.d(TAG, " updateMessage ");
        mDefaultMessage.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_view_menu_item, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        mSearchViewItem = menu.findItem(R.id.search);
        mSearchView = (SearchView) mSearchViewItem.getActionView();

        mSearchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        mSearchView.setSubmitButtonEnabled(false);
        mSearchView.setIconifiedByDefault(false);
        mSearchView.setOnQueryTextListener(this);

        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Log.d(TAG, " onQueryTextSubmit  " + query);
        if (NetworkUtils.getInstance().isNetworkAvailable(MainActivity.this)) {
            saveQuery(query);
            prepareForNewList();
            hideKeyboard();
            mMainPresenterImpl.getTweetsWithHashTagFromServer(query);
            mMainPresenterImpl.schedulePollingForNewTweets(query);
        } else {
            // load from database when offline
            mMainPresenterImpl.getTweetsFromTableWithSearchKey(query);
        }
        if (mSearchView != null) {
            mSearchView.clearFocus();
        }
        return true;
    }

    @Override
    public void saveQuery(@NonNull String query) {
        SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this,
                SearchSuggestionProvider.AUTHORITY, SearchSuggestionProvider.MODE);
        suggestions.saveRecentQuery(query, null);
    }

    @Override
    public void clearQueryHistory() {
        SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this,
                SearchSuggestionProvider.AUTHORITY, SearchSuggestionProvider.MODE);
        suggestions.clearHistory();
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public void addNewTweetToList(@NonNull StatusesItem results) {
        Log.d(TAG, " addNewTweetToList ");
        if (results != null) {
            // Add new user to list
            mTweetsAdapter.addNewTweet(results);
            mTweetsAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void prepareForNewList() {
        loading(R.string.loading_tweets);
        updateMessage(true);
        mTweetsAdapter.getTweetItems().clear();
        mMainPresenterImpl.stopPollingScheduler();
    }

    @Override
    public void showEmptyList() {
        Log.d(TAG, " showEmptyList ");
        mDefaultMessage.setText(getText(R.string.error_message));
        mTweetsAdapter.replaceWithNewList(null);
        mTweetsAdapter.notifyDataSetChanged();
        onError();
    }

    @Override
    public void loading(@NonNull int resource) {
        Log.d(TAG, " loading ");
        mDefaultMessage.setText(getText(resource));
    }

    @Override
    public void addTweetsToList(@NonNull List<StatusesItem> statusesItems) {
        Log.d(TAG, " addTweetsToList received size " + statusesItems.size());
        if (statusesItems != null && statusesItems.size() > 0) {
            if (mTweetsAdapter.getItemCount() == 0) {
                mTweetItems.addAll(statusesItems);
                mTweetsAdapter.addList(mTweetItems);
                mTweetsAdapter.notifyDataSetChanged();
            } else {
                mMainPresenterImpl.processNewTweets(mTweetsAdapter.getTweetItems().get(0).getTweetCreationTime(), statusesItems);
            }
        }
    }

    @Override
    public void replaceTweetsWithNewInList(@NonNull List<StatusesItem> results) {
        Log.d(TAG, " replaceTweetsWithNewInList received size " + results.size());
        if (results != null && results.size() > 0) {
            mTweetsAdapter.replaceWithNewList(results);
            mTweetsAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onSuccess() {
        Log.d(TAG, " onSuccess ");
        updateMessage(false);
    }

    @Override
    public void onError() {
        Log.d(TAG, " onError ");
        updateMessage(true);
    }

    @Override
    public void onError(@StringRes int resource) {
        Log.d(TAG, " onError ");
    }

    @Override
    public void hideKeyboard() {
        Log.d(TAG, " hideKeyboard ");
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void subscribeToTweetViewModel() {
        Log.d(TAG, " subscribeToTweetViewModel");
    }

    @Override
    public void UnSubscribeFromTweetViewModel() {
        Log.d(TAG, " UnSubscribeFromTweetViewModel");
    }
}

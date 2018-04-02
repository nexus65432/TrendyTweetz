package com.nexus.trendytweetz.ui.onboarding;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.nexus.trendytweetz.ui.BaseActivity;
import com.nexus.trendytweetz.ui.MainActivity;
import com.nexus.trendytweetz.R;


public class SplashActivity extends BaseActivity implements SplashView {

    public static final int SPLASH_DISPLAY_INTERVAL = 1000;

    SplashPresenterImpl mPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.splash_layout);

        mPresenter = new SplashPresenterImpl(this);

        // Calling this as we are not validating at this time.
        mPresenter.onLoginSuccess();
    }

    // TODO: Implement logic to show Login screen
    @Override
    public void showLoginActivity() {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                showMainActivity();
            }
        }, SPLASH_DISPLAY_INTERVAL);
    }

    @Override
    public void openMainActivity() {
        showMainActivity();
    }

    private void showMainActivity() {
        Intent intent = MainActivity.getStartIntent(SplashActivity.this);
        startActivity(intent);
        finish();
    }
}

package com.nexus.trendytweetz.ui.onboarding;


public class SplashPresenterImpl implements SplashPresenter {

    private SplashView mSplashView;

    public SplashPresenterImpl(SplashView view) {
        this.mSplashView = view;
    }

    @Override
    public void onLoginSuccess() {
        mSplashView.openMainActivity();
    }

    @Override
    public void onLoginFailed() {
        mSplashView.showLoginActivity();
    }

    @Override
    public void onDetach() {

    }
}

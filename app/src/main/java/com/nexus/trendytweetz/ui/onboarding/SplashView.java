package com.nexus.trendytweetz.ui.onboarding;


public interface SplashView {

    /**
     * show Login Activity if user is not authenticated
     */
    void showLoginActivity();

    /**
     * Take user to MainActivity if user is authenticated
     */
    void openMainActivity();
}

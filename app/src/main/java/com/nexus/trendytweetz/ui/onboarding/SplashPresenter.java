package com.nexus.trendytweetz.ui.onboarding;


public interface SplashPresenter {

    /**
     * On user successfully authenticated
     */
    void onLoginSuccess();

    /**
     * When user authentication failed
     */
    void onLoginFailed();

    /**
     * Cleanup resources when activity is destroyed
     */
    void onDetach();
}

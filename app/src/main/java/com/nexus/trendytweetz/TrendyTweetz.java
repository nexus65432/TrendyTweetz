package com.nexus.trendytweetz;

import android.app.Application;

/**
 * Main class which will be called first when user opens the application.
 */
public class TrendyTweetz extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Initialize any databases or 3rd party libraries.
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }
}

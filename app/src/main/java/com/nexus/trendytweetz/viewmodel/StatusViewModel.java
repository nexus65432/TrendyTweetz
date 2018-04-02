package com.nexus.trendytweetz.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

/**
 * To observe on the View for any change in events
 */
public class StatusViewModel extends AndroidViewModel {

    public StatusViewModel(@NonNull Application application) {
        super(application);
    }
}

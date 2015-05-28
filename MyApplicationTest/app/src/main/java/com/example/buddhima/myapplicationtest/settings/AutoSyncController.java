package com.example.buddhima.myapplicationtest.settings;

import android.content.ContentResolver;

/**
 * Created by sunethe on 5/28/2015.
 */
public class AutoSyncController {
    public void enableAutoSync(ContentResolver cResolver) {
        cResolver.setMasterSyncAutomatically(true);
    }

    public void disableAutoSync(ContentResolver cResolver) {

        cResolver.setMasterSyncAutomatically(false);
    }
}

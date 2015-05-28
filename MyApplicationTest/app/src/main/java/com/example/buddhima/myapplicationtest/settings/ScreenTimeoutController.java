package com.example.buddhima.myapplicationtest.settings;

import android.content.ContentResolver;

/**
 * Created by sunethe on 5/28/2015.
 */
public class ScreenTimeoutController {


    public void setTimeout(ContentResolver cResolver, int screenOffTimeout) {
        int time;
        switch (screenOffTimeout) {
            case 0:
                time = 15000;
                break;
            case 1:
                time = 30000;
                break;
            case 2:
                time = 60000;
                break;
            case 3:
                time = 120000;
                break;
            case 4:
                time = 600000;
                break;
            case 5:
                time = 1800000;
                break;
            default:
                time = -1;
        }
        android.provider.Settings.System.putInt(cResolver,
                android.provider.Settings.System.SCREEN_OFF_TIMEOUT, time);
    }
}

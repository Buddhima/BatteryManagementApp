package com.example.buddhima.myapplicationtest.settings;


import android.app.ActionBar;
import android.app.Activity;
import android.content.ContentResolver;
import android.provider.Settings;
import android.provider.Settings.SettingNotFoundException;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import android.app.Activity;
import android.content.ContentResolver;
import android.os.Bundle;
import android.provider.Settings.SettingNotFoundException;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;


/**
 * Created by sunethe on 5/28/2015.
 */
public class BrightnessController {

    private int brightness;
    private static int MAX_BRIGHTNESS = 255;

    public BrightnessController(ContentResolver cResolver) {
        getBrightness(cResolver);
    }

    public int getBrightness(ContentResolver cResolver) {
        try {
            brightness = android.provider.Settings.System.getInt(cResolver, android.provider.Settings.System.SCREEN_BRIGHTNESS);
            System.out.println("BRIGHTNESS : " + brightness);
            return brightness;
        } catch (SettingNotFoundException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public void setBrightness(Window window, ContentResolver cResolver, int brightness) {
        if (brightness > MAX_BRIGHTNESS)
            brightness = MAX_BRIGHTNESS;

        android.provider.Settings.System.putInt(cResolver, android.provider.Settings.System.SCREEN_BRIGHTNESS, brightness);
        LayoutParams layoutpars = window.getAttributes();
        layoutpars.screenBrightness = (float) brightness;
        window.setAttributes(layoutpars);
    }

}

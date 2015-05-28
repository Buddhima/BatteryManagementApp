package com.example.buddhima.myapplicationtest.settings;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.support.v7.app.ActionBarActivity;

/**
 * Created by sunethe on 5/28/2015.
 */
public class WiFiController {

    public void enableWiFi(ActionBarActivity activity){
        WifiManager wifiManager = (WifiManager) activity.getSystemService(Context.WIFI_SERVICE);
        if(!wifiManager.isWifiEnabled())
            wifiManager.setWifiEnabled(true);
    }

    public void disableWiFi(ActionBarActivity activity){
        WifiManager wifiManager = (WifiManager) activity.getSystemService(Context.WIFI_SERVICE);
        if(wifiManager.isWifiEnabled())
            wifiManager.setWifiEnabled(false);
    }
}


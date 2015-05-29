package com.example.buddhima.myapplicationtest.settings;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;

import com.example.buddhima.myapplicationtest.Globals;

/**
 * Created by sunethe on 5/29/2015.
 */
public class BatteryMonitor extends Thread {

    //Controllers
    BrightnessController brightnessController;
    BluetoothController bluetoothController;
    WiFiController wiFiController;
    AutoSyncController autoSyncController;
    ScreenTimeoutController screenTimeoutController;

    Activity activity;

    //popup
    ScreenTimeoutPopup screenTimeoutPopup;

    public BatteryMonitor(Activity activity) {
        //controllers
        brightnessController = new BrightnessController(Globals.cResolver);
        bluetoothController = new BluetoothController();
        wiFiController = new WiFiController();
        autoSyncController = new AutoSyncController();
        screenTimeoutController = new ScreenTimeoutController();
        screenTimeoutPopup = new ScreenTimeoutPopup();

        this.activity = activity;

    }

    public void run() {
        while (true) {
            if (Globals.actionListContext == null)
                return;

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("<<<<<<<<<<<<<<NOT NULL <<<<<<<<<<<<<<<< ");

            Globals.actionListContext.printActionContext();

            if (Globals.getBatteryLevel() < 50) {
                System.out.println("<<<<<<<<<<<<<<50 LESS THAN <<<<<<<<<<<<<<<< ");
                if (Globals.isPhase1Active) {
                    //testing
                    if (Globals.arrayActionStatus[0][0])
                        wiFiController.disableWiFi((android.support.v7.app.ActionBarActivity) activity);
                    else if (Globals.arrayActionStatus[0][1])
                        bluetoothController.disableBluetooth();
                    else if (Globals.arrayActionStatus[0][2])
                        brightnessController.setBrightness(activity.getWindow(), activity.getContentResolver(), brightnessController.getBrightness(activity.getContentResolver()) - 25);

                }

                if (Globals.isPhase2Active) {
                    if (Globals.arrayActionStatus[1][0])
                        bluetoothController.disableBluetooth();
                    else if (Globals.arrayActionStatus[1][1])
                        screenTimeoutController.setTimeout(activity.getContentResolver(), Globals.timeout);
                    else if (Globals.arrayActionStatus[1][2])
                        autoSyncController.disableAutoSync(activity.getContentResolver());
                }
            }
        }
    }


}

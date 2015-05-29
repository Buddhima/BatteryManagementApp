package com.example.buddhima.myapplicationtest;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.BatteryManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.buddhima.myapplicationtest.settings.AutoSyncController;
import com.example.buddhima.myapplicationtest.settings.BatteryMonitor;
import com.example.buddhima.myapplicationtest.settings.BluetoothController;
import com.example.buddhima.myapplicationtest.settings.BrightnessController;
import com.example.buddhima.myapplicationtest.settings.ScreenTimeoutController;
import com.example.buddhima.myapplicationtest.settings.ScreenTimeoutPopup;
import com.example.buddhima.myapplicationtest.settings.SettingsActivity;
import com.example.buddhima.myapplicationtest.settings.WiFiController;


public class MainActivity extends ActionBarActivity {

    //Controllers
    static BrightnessController brightnessController;
    static BluetoothController bluetoothController;
    static WiFiController wiFiController;
    static AutoSyncController autoSyncController;
    static ScreenTimeoutController screenTimeoutController;
    //popup
    static ScreenTimeoutPopup screenTimeoutPopup;

    ActionBarActivity actionBarActivity;

    static Switch enablingSwitch;
    private static Bundle bundle = new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        brightnessController = new BrightnessController(Globals.cResolver);
        bluetoothController = new BluetoothController();
        wiFiController = new WiFiController();
        autoSyncController = new AutoSyncController();
        screenTimeoutController = new ScreenTimeoutController();
        screenTimeoutPopup = new ScreenTimeoutPopup();


        actionBarActivity = this;
        enablingSwitch = (Switch) findViewById(R.id.switch1);

        Globals.cResolver = getContentResolver();
        setBatteryLevel();
        startMonitoring();
    }

    /**
     * Set the current battery-level
     */
    private void setBatteryLevel() {
        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = getBaseContext().registerReceiver(null, ifilter);
        int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);

        ProgressBar batteryBar = (ProgressBar) findViewById(R.id.batteryBar);
        TextView progressText = (TextView) findViewById(R.id.progressText);

        batteryBar.setProgress(level);
        progressText.setText(level + "%");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        } else
        if (id == R.id.action_locations) {
            // Switch to location list page
            Intent intent = new Intent(this, LocationListActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    public void onClickRetrieveLocations(View view) {
        // Retrieve student records
        String URL = "content://com.example.provider.AndroidApp/locations";
        Uri locations = Uri.parse(URL);
        Cursor c = managedQuery(locations, null, null, null, "address");
        if (c.moveToFirst()) {
            do {
                Toast.makeText(this,
                        c.getString(c.getColumnIndex(LocationProvider._ID)) +
                                ", " + c.getString(c.getColumnIndex(LocationProvider.ADDRESS)) +
                                ", " + c.getString(c.getColumnIndex(LocationProvider.COUNT)),
                        Toast.LENGTH_SHORT).show();
            } while (c.moveToNext());
        }
    }

    //suneth : open the settings activity
    public void openSettings(View view) {
        Intent settingsIntent = new Intent(this, SettingsActivity.class);
        startActivity(settingsIntent);
    }

    public void startMonitoring() {
//        BatteryMonitor bm = new BatteryMonitor(this);
//        bm.start();

        System.out.println("<<<<<<<<<<<<<<<<<<<<<< startMonitoring");
        final Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                continueMonitoring();
                //testinstance();
            }
        });

        t1.start();

    }

    public void testinstance() {
        while (true)
            System.out.println("TESTINSTANCE123");
    }

    public static void test() {
        while (true)
            System.out.println("TEST");
    }

    public void continueMonitoring() {
        while (true) {

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("continue <<<<<<<<<<<<<<<<<<");
//            enablingSwitch = (Switch) findViewById(R.id.switch1);
//            enablingSwitch.setChecked(Globals.isAllEnabled);

            if (!Globals.isAllEnabled)
                continue;

            if (Globals.actionListContext == null)
                continue;

            Globals.actionListContext.printActionContext();

            if (Globals.getBatteryLevel() < 50) {
                System.out.println("<<<<<<<<<<<<<<50 LESS THAN <<<<<<<<<<<<<<<< ");
                if (Globals.isPhase1Active) {
                    //testing
                    if (Globals.arrayActionStatus[0][0]) {
                        wiFiController.disableWiFi(this);
                        showToast("WiFi Switched off");

                    }
                    if (Globals.arrayActionStatus[0][1]) {
                        bluetoothController.disableBluetooth();
                        showToast("Bluetooth switched off");

                    }
                    if (Globals.arrayActionStatus[0][2]) {
                        brightnessController.setBrightness(getWindow(), getContentResolver(), brightnessController.getBrightness(getContentResolver()) - 25);
                        showToast("Brightness reduced");
                    }

                }

                if (Globals.isPhase2Active) {
                    if (Globals.arrayActionStatus[1][0]) {
                        bluetoothController.disableBluetooth();
                        showToast("Bluetooth switched off");

                    }
                    if (Globals.arrayActionStatus[1][1]) {
                        screenTimeoutController.setTimeout(getContentResolver(), Globals.timeout);
                        showToast("Screen timeout set");

                    }
                    if (Globals.arrayActionStatus[1][2]) {
                        autoSyncController.disableAutoSync(getContentResolver());

                        showToast("AutoSync switched off");
                    }
                }
            }
        }
    }

    public void sleep() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    public void showToast(final String toast) {
        runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(actionBarActivity, toast, Toast.LENGTH_SHORT).show();
            }
        });
        sleep();
    }

    public void onEnableButtonClicked(View view) {
        Switch sw = (Switch) view;
        Globals.isAllEnabled = sw.isChecked();
        if (Globals.isAllEnabled) {

            Toast.makeText(actionBarActivity, "App Enabled", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(actionBarActivity, "App Disabled", Toast.LENGTH_SHORT).show();


        }
        sw.setChecked(Globals.isAllEnabled);
        sw.setPressed(Globals.isAllEnabled);

    }


    @Override
    public boolean onNavigateUpFromChild(Activity child) {
        System.out.println("CHILD ><<<<<<<<<<<<<<<<<<");
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        bundle.putBoolean("ToggleButtonState", enablingSwitch.isChecked());
    }

    @Override
    protected void onResume() {
        super.onPause();
        enablingSwitch.setChecked(bundle.getBoolean("ToggleButtonState",false));
    }
}



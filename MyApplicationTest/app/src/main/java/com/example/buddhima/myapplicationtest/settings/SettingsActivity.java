package com.example.buddhima.myapplicationtest.settings;


import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;

import com.example.buddhima.myapplicationtest.R;
import com.example.buddhima.myapplicationtest.bean.ActionListContext;

//suneth
public class SettingsActivity extends ActionBarActivity {

    ActionListContext actionListContext;

    //Controllers
    BrightnessController brightnessController;
    BluetoothController bluetoothController;
    WiFiController wiFiController;
    AutoSyncController autoSyncController;
    ScreenTimeoutController screenTimeoutController;

    //popup
    ScreenTimeoutPopup screenTimeoutPopup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //create actionListContext
        LinearLayout layoutPhase1 = (LinearLayout) findViewById(R.id.linearPhase1);
        LinearLayout layoutPhase2 = (LinearLayout) findViewById(R.id.linearPhase2);
        actionListContext = new ActionListContext(layoutPhase1, layoutPhase2);

        Switch sw1 = (Switch) findViewById(R.id.switchPhase1);
        Switch sw2 = (Switch) findViewById(R.id.switchPhase2);

        actionListContext.setPhase1Active(sw1.isChecked());
        actionListContext.setPhase2Active(sw2.isChecked());

        //controllers
        brightnessController = new BrightnessController(getContentResolver());
        bluetoothController = new BluetoothController();
        wiFiController = new WiFiController();
        autoSyncController = new AutoSyncController();
        screenTimeoutController = new ScreenTimeoutController();

        screenTimeoutPopup = new ScreenTimeoutPopup();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onCheckboxClicked(View view) {
        CheckedTextView ctv = (CheckedTextView) view;
        String actionName = ctv.getText().toString();

        if (ctv.isChecked())
            ctv.setChecked(false);
        else
            ctv.setChecked(true);

        if (actionListContext.setActionStatus(actionName, ctv.isChecked())) {
            //open the timeout popup
            screenTimeoutPopup.showPopup(this, actionListContext.getPopupLocation(), actionListContext.getTimeout());
        }
        //testing
//        brightnessController.setBrightness(getWindow(), getContentResolver(), brightnessController.getBrightness(getContentResolver()) + 25);
//        bluetoothController.disableBluetooth();
//        wiFiController.disableWiFi(this);
//        autoSyncController.disableAutoSync(getContentResolver());
    }

    public void onSwitchPhase1Clicked(View view) {

        Switch sw = ((Switch) view);
        actionListContext.setPhase1Active(sw.isChecked());
    }

    public void onSwitchPhase2Clicked(View view) {
        Switch sw = ((Switch) view);
        actionListContext.setPhase2Active(sw.isChecked());
    }


    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        int radioButtonLocation = -1;
        if (!checked)
            return;

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.radio_15s:
                radioButtonLocation = 0;

                break;
            case R.id.radio_30s:
                radioButtonLocation = 1;

                break;
            case R.id.radio_1m:
                radioButtonLocation = 2;

                break;
            case R.id.radio_2m:
                radioButtonLocation = 3;

                break;
            case R.id.radio_10m:
                radioButtonLocation = 4;

                break;
            case R.id.radio_30m:
                radioButtonLocation = 5;

                break;
        }

        actionListContext.setTimeout(radioButtonLocation);
//        screenTimeoutController.setTimeout(getContentResolver(), radioButtonLocation);
    }
}
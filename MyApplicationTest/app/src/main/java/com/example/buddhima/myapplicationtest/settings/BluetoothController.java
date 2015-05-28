package com.example.buddhima.myapplicationtest.settings;

import android.bluetooth.BluetoothAdapter;

/**
 * Created by sunethe on 5/28/2015.
 */
public class BluetoothController {

    public void disableBluetooth(){
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter.isEnabled()) {
            mBluetoothAdapter.disable();
        }
    }

    public void enableBluetooth(){
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (!mBluetoothAdapter.isEnabled()) {
            mBluetoothAdapter.enable();
        }
    }
}

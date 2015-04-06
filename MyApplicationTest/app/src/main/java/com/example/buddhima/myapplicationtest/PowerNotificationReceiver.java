package com.example.buddhima.myapplicationtest;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.location.Geocoder;
import android.net.Uri;
import android.widget.Toast;

import java.io.IOException;
import java.util.Locale;

public class PowerNotificationReceiver extends BroadcastReceiver {
    public PowerNotificationReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.

        if (intent.getAction().equals(Intent.ACTION_POWER_CONNECTED)){
            Toast.makeText(context, "Power Connected", Toast.LENGTH_LONG).show();

            Geocoder geocoder = new Geocoder(context, Locale.getDefault());
            try {
                Toast.makeText(context, geocoder.getFromLocation(6.931944, 79.847778, 1).get(0).getAddressLine(0), Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                e.printStackTrace();
            }

            ContentValues values = new ContentValues();
            values.put(LocationProvider.ADDRESS, "dummy address");
            values.put(LocationProvider.LONGITUDE, "0.0");
            values.put(LocationProvider.LATITUDE, "0.0");
            values.put(LocationProvider.COUNT, "0");

            Uri uri = context.getContentResolver().insert(LocationProvider.CONTENT_URI, values);

            Toast.makeText(context, uri.toString(), Toast.LENGTH_LONG).show();

        }
    }
}

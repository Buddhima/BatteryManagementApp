package com.example.buddhima.myapplicationtest;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.location.Geocoder;
import android.net.Uri;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.drive.Drive;

import java.io.IOException;
import java.util.Locale;

public class PowerNotificationReceiver extends BroadcastReceiver {

    private GPSDataProvider gpsDataProvider;

    public PowerNotificationReceiver() {

    }

    @Override
    public void onReceive(Context context, Intent intent) {

        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.


        GoogleApiClient mGoogleApiClient = new GoogleApiClient.Builder(context)
                                            .build();
        gpsDataProvider = new GPSDataProvider(mGoogleApiClient);
        double lat = gpsDataProvider.getCurrentLatitude();
        double lon = gpsDataProvider.getCurrentLongitude();

        if (intent.getAction().equals(Intent.ACTION_POWER_CONNECTED)){
            Toast.makeText(context, "Power Connected", Toast.LENGTH_LONG).show();

            Geocoder geocoder = new Geocoder(context, Locale.getDefault());
            String address = "";

            try {
                address = geocoder.getFromLocation(lat, lon, 1).get(0).getAddressLine(0);

                Toast.makeText(context, geocoder.getFromLocation(lat, lon, 1).get(0).getAddressLine(0), Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                e.printStackTrace();
            }

            ContentValues values = new ContentValues();
            values.put(LocationProvider.ADDRESS, address);
            values.put(LocationProvider.LONGITUDE, String.valueOf(lon));
            values.put(LocationProvider.LATITUDE, String.valueOf(lat));
            values.put(LocationProvider.COUNT, "0");

            Uri uri = context.getContentResolver().insert(LocationProvider.CONTENT_URI, values);

            Toast.makeText(context, uri.toString(), Toast.LENGTH_LONG).show();

        }
    }
}

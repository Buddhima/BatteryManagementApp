package com.example.buddhima.myapplicationtest;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.location.Geocoder;
import android.net.Uri;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.util.Date;
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
                                            .addApi(LocationServices.API)
                                            .build();
        gpsDataProvider = new GPSDataProvider(mGoogleApiClient);
        double lat = 6.0502556 ; //gpsDataProvider.getCurrentLatitude();
        double lon = 80.2153525; //gpsDataProvider.getCurrentLongitude();

        if (intent.getAction().equals(Intent.ACTION_POWER_CONNECTED)){
            Toast.makeText(context, "Power Connected", Toast.LENGTH_LONG).show();

            Geocoder geocoder = new Geocoder(context, Locale.getDefault());
            String address = "";

            try {
//                address = geocoder.getFromLocation(lat, lon, 1).get(0).getAddressLine(0);
                address = geocoder.getFromLocation(lat, lon, 1).get(0).getAddressLine(0)
                        +", "
                        + geocoder.getFromLocation(lat, lon, 1).get(0).getAddressLine(1);

                if (address.trim() == ""){
                    System.out.println("Location can't find");
                    return;
                }

                Toast.makeText(context, address, Toast.LENGTH_LONG).show();

                if(alreadyExists(context, lat, lon)) {
                    System.out.println("Location already exists");
                    return;
                }


            } catch (IOException e) {
                e.printStackTrace();
            }

            ContentValues values = new ContentValues();
            values.put(LocationProvider.ADDRESS, address);
            values.put(LocationProvider.LONGITUDE, String.valueOf(lon));
            values.put(LocationProvider.LATITUDE, String.valueOf(lat));
            values.put(LocationProvider.COUNT, "1");
            values.put(LocationProvider.LAST_CHARGED, new Date().toString());

            Uri uri = context.getContentResolver().insert(LocationProvider.CONTENT_URI, values);

            Toast.makeText(context, uri.toString(), Toast.LENGTH_LONG).show();

        }
    }

    private boolean alreadyExists(Context context, double latitude, double longitude) {

        Cursor c = context.getContentResolver().query(LocationProvider.CONTENT_URI, null, null, null, null);

        int index = 0;

        // Construct arrays to display
        if (c.moveToFirst()) {
            do{

                double storedLatitude = c.getDouble(c.getColumnIndex(LocationProvider.LATITUDE));
                double storedLongitude = c.getDouble(c.getColumnIndex(LocationProvider.LONGITUDE));
                int count = c.getInt(c.getColumnIndex(LocationProvider.COUNT));

                GPSDataProvider gdp = new GPSDataProvider();
                GPSDataProvider.GeodataCalculator geodataCalculator = gdp.new GeodataCalculator();

                double distance = geodataCalculator.distance(latitude, longitude, storedLatitude, storedLongitude, 'K');

                if (distance < 2)
                {
                    ContentValues values = new ContentValues();
//                    values.put(LocationProvider._ID, c.getString(c.getColumnIndex(LocationProvider._ID)));
                    values.put(LocationProvider.COUNT, String.valueOf(count+1));
//                    values.put(LocationProvider.LAST_CHARGED, new Date().toString());


                    context.getContentResolver().update(LocationProvider.CONTENT_URI, values, LocationProvider._ID+"=?", new String[]{c.getString(c.getColumnIndex(LocationProvider._ID))});
                    return true;
                }


                index ++;

            } while (c.moveToNext());
        }

        return false;
    }
}

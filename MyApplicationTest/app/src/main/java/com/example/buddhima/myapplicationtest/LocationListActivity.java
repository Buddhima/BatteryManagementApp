package com.example.buddhima.myapplicationtest;

import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;


public class LocationListActivity extends ActionBarActivity {

    ListView locationList;

    String[] address, cordinates, info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_list);

        // Fill location data
        fillLocationDetails();

        // Set new adaptor for the list view
        CustomListLocation adapter = new CustomListLocation(LocationListActivity.this, address, cordinates, info);

        locationList = (ListView)findViewById(R.id.listView);
        locationList.setAdapter(adapter);

    }

    private void fillLocationDetails() {
        // Retrieve student records
        String URL = "content://com.example.provider.AndroidApp/locations";
        Uri locations = Uri.parse(URL);
        Cursor c = managedQuery(locations, null, null, null, "address");

        address    = new String[c.getCount()];
        cordinates = new String[c.getCount()];
        info       = new String[c.getCount()];

        int index = 0;

        // Construct arrays to display
        if (c.moveToFirst()) {
            do{
                address[index] = c.getString(c.getColumnIndex(LocationProvider.ADDRESS));
                cordinates[index] = c.getString(c.getColumnIndex(LocationProvider.LATITUDE)) + " , " + c.getString(c.getColumnIndex(LocationProvider.LONGITUDE));
                info[index] = "You've charged " + c.getString(c.getColumnIndex(LocationProvider.COUNT)) + " times there";

                index ++;

            } while (c.moveToNext());
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_location_list, menu);
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
//        }

        return super.onOptionsItemSelected(item);
    }
}

package com.example.buddhima.myapplicationtest;

import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;


public class LocationListActivity extends ActionBarActivity {

    ListView locationList;

    String[] address, cordinates, info, ids;

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

        locationList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println(locationList.getItemAtPosition(position).toString());

                String dbId = ids[position];

                // Show delete confirmation dialog
                showConfirmationDialog(dbId);


                return true;
            }
        });
    }

    private void showConfirmationDialog(String id){

        final String dbId = id;

        new AlertDialog.Builder(this)
                .setTitle("Delete Confirmation")
                .setMessage("Do you want to remove this location entry?")
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getContentResolver().delete(LocationProvider.CONTENT_URI, LocationProvider._ID+"=?", new String[] {dbId});
                        refreshScreen();
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();

    }

    private void fillLocationDetails() {
        // Retrieve student records
//        String URL = "content://com.example.provider.AndroidApp/locations";
        Uri locations = Uri.parse(LocationProvider.URL);
        Cursor c = managedQuery(locations, null, null, null, LocationProvider.ADDRESS);

        address    = new String[c.getCount()];
        cordinates = new String[c.getCount()];
        info       = new String[c.getCount()];
        ids       = new String[c.getCount()];

        int index = 0;

        // Construct arrays to display
        if (c.moveToFirst()) {
            do{
                address[index] = c.getString(c.getColumnIndex(LocationProvider.ADDRESS));
                cordinates[index] = c.getString(c.getColumnIndex(LocationProvider.LATITUDE)) + " , " + c.getString(c.getColumnIndex(LocationProvider.LONGITUDE));
                info[index] = "You've charged " + c.getString(c.getColumnIndex(LocationProvider.COUNT)) + " times there \n";
                info[index] += "Last charged on " + c.getString(c.getColumnIndex(LocationProvider.LAST_CHARGED));
                ids[index] = c.getString(c.getColumnIndex(LocationProvider._ID));

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
        if (id == R.id.action_clear_location_list) {
            Toast.makeText(this.getBaseContext(), "Clearing data", Toast.LENGTH_SHORT ).show();
            clearLocationsList();

            // Refresh the screen
            this.refreshScreen();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void clearLocationsList() {
        int result = this.getContentResolver().delete(LocationProvider.CONTENT_URI, null, null);
    }

    private void refreshScreen(){
        // Refresh the screen
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }
}

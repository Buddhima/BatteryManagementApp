package com.example.buddhima.myapplicationtest;

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
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setBatteryLevel();
    }

    /**
     * Set the current battery-level
     */
    private void setBatteryLevel() {
        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = getBaseContext().registerReceiver(null, ifilter);
        int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);

        ProgressBar batteryBar = (ProgressBar)findViewById(R.id.batteryBar);
        TextView progressText  = (TextView)findViewById(R.id.progressText);

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
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_locations) {
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
            do{
                Toast.makeText(this,
                        c.getString(c.getColumnIndex(LocationProvider._ID)) +
                                ", " + c.getString(c.getColumnIndex(LocationProvider.ADDRESS)) +
                                ", " + c.getString(c.getColumnIndex(LocationProvider.COUNT)),
                        Toast.LENGTH_SHORT).show();
            } while (c.moveToNext());
        }
    }
}

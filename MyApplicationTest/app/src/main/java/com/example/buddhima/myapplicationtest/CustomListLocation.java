package com.example.buddhima.myapplicationtest;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Class to represent a customized single list-item in locations list
 */
public class CustomListLocation extends ArrayAdapter<String> {

    private final String[] address, cordinates, info;
    private final Activity context;

    public CustomListLocation(Activity context, String[] address, String[] cordinates, String[] info) {
        super(context, R.layout.location_list_item, address);

        this.address    = address;
        this.cordinates = cordinates;
        this.info       = info;

        this.context    = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.location_list_item, null, true);

        TextView addressTextView    = (TextView) rowView.findViewById(R.id.addressTextView);
        TextView cordinatesTextView = (TextView) rowView.findViewById(R.id.cordinatesTextView);
        TextView infoTextView       = (TextView) rowView.findViewById(R.id.infoTextView);

        addressTextView.setText(address[position]);
        cordinatesTextView.setText(cordinates[position]);
        infoTextView.setText(info[position]);

        return rowView;
    }
}

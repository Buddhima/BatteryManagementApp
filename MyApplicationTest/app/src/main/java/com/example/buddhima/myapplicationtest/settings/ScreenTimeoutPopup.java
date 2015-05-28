package com.example.buddhima.myapplicationtest.settings;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;

import com.example.buddhima.myapplicationtest.R;

/**
 * Created by sunethe on 5/28/2015.
 */
public class ScreenTimeoutPopup {
    // The method that displays the popup.

    View layout;

    public void showPopup(final Activity context, Point p, int radioButtonLocation) {
        int popupWidth = 350;
        int popupHeight = 500;

        // Inflate the popup_layout.xml
        LinearLayout viewGroup = (LinearLayout) context.findViewById(R.id.popup);
        LayoutInflater layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layout = layoutInflater.inflate(R.layout.layout_timeout_popup, viewGroup);

        // Creating the PopupWindow
        final PopupWindow popup = new PopupWindow(context);
        popup.setContentView(layout);
        popup.setWidth(popupWidth);
        popup.setHeight(popupHeight);
        popup.setFocusable(true);

        // Some offset to align the popup a bit to the right, and a bit down, relative to button's position.
        int OFFSET_X = 30;
        int OFFSET_Y = 30;

        // Clear the default translucent background
        popup.setBackgroundDrawable(new BitmapDrawable());

        // Displaying the popup at the specified location, + offsets.
        popup.showAtLocation(layout, Gravity.NO_GRAVITY, p.x + OFFSET_X, p.y + OFFSET_Y);

        checkRadioButton(radioButtonLocation);

        // Getting a reference to Close button, and close the popup when clicked.
        Button close = (Button) layout.findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                popup.dismiss();
            }
        });
    }

    public void checkRadioButton(int rbID) {

        RadioButton radioButton = null;
        switch (rbID) {
            case 0:
                radioButton = (RadioButton) layout.findViewById(R.id.radio_15s);
                break;
            case 1:
                radioButton = (RadioButton) layout.findViewById(R.id.radio_30s);
                break;
            case 2:
                radioButton = (RadioButton) layout.findViewById(R.id.radio_1m);
                break;
            case 3:
                radioButton = (RadioButton) layout.findViewById(R.id.radio_2m);
                break;
            case 4:
                radioButton = (RadioButton) layout.findViewById(R.id.radio_10m);
                break;
            case 5:
                radioButton = (RadioButton) layout.findViewById(R.id.radio_30m);
                break;
        }

        radioButton.setChecked(true);
    }
}

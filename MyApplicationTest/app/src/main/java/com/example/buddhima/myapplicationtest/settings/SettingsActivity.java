package com.example.buddhima.myapplicationtest.settings;

import android.app.ExpandableListActivity;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.example.buddhima.myapplicationtest.R;
import com.example.buddhima.myapplicationtest.bean.ActionList;

import java.util.ArrayList;

public class SettingsActivity extends ExpandableListActivity {

    private ArrayList<ActionList> listOfActionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Resources res = this.getResources();
        Drawable devider = res.getDrawable(R.drawable.line);

        // Set ExpandableListView values

        getExpandableListView().setGroupIndicator(null);
        getExpandableListView().setDivider(devider);
        getExpandableListView().setChildDivider(devider);
        getExpandableListView().setDividerHeight(1);
        registerForContextMenu(getExpandableListView());

        //Creating static data in arraylist
        final ArrayList<ActionList> actionLists = populateActions();

        // Adding ArrayList data to ExpandableListView values//
        loadActionLists(actionLists);
    }

    private ArrayList<ActionList> populateActions() {

        ArrayList<ActionList> list = new ArrayList<ActionList>();

        String[] actionsPhase1 = new String[]{"Action1", "Action1", "Action1", "Action1"};
        String[] actionsPhase2 = new String[]{"Action21", "Action21", "Action21", "Action21"};
        String[] actionsPhase3 = new String[]{"Action31", "Action31", "Action31", "Action31"};

        list.add(new ActionList(1, actionsPhase1));
        list.add(new ActionList(2, actionsPhase2));
        list.add(new ActionList(3, actionsPhase3));

        return list;
    }


    private void loadActionLists(final ArrayList<ActionList> newActionLists) {
        if (newActionLists == null)
            return;

        listOfActionList = newActionLists;

        // Check for ExpandableListAdapter object
        if (this.getExpandableListAdapter() == null) {
            //Create ExpandableListAdapter Object
            final MyExpandableListAdapter mAdapter = new MyExpandableListAdapter(getApplicationContext(), listOfActionList);
            // Set Adapter to ExpandableList Adapter
            this.setListAdapter(mAdapter);
        } else {
            // Refresh ExpandableListView data
            ((MyExpandableListAdapter) getExpandableListAdapter()).notifyDataSetChanged();
        }
    }
}

/***********************************************************************/


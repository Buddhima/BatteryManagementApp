package com.example.buddhima.myapplicationtest.settings;

import android.app.ExpandableListActivity;
import android.content.Context;
import android.util.Log;
import android.widget.CompoundButton;

import com.example.buddhima.myapplicationtest.bean.ActionList;

/******************* Checkbox Checked Change Listener ********************/

public class CheckUpdateListener extends ExpandableListActivity implements CompoundButton.OnCheckedChangeListener
{
    private final ActionList parent;
    private Context context;
    private  MyExpandableListAdapter myExpandableListAdapter;

    public CheckUpdateListener(ActionList parent, Context context, MyExpandableListAdapter myExpandableListAdapter)
    {
        this.parent = parent;
        this.context=context;
        this.myExpandableListAdapter=myExpandableListAdapter;
    }
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
    {
        Log.i("onCheckedChanged", "isChecked: " + isChecked);
        parent.setChecked(isChecked);

        myExpandableListAdapter.notifyDataSetChanged();

        final Boolean checked = parent.isChecked();
//            Toast.makeText(getApplicationContext(), "Parent : " + parent.getName() + " " + (checked ? STR_CHECKED : STR_UNCHECKED),
//                    Toast.LENGTH_LONG).show();
    }
}

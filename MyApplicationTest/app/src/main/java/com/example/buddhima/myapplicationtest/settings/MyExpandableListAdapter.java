package com.example.buddhima.myapplicationtest.settings;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.buddhima.myapplicationtest.R;
import com.example.buddhima.myapplicationtest.bean.Action;
import com.example.buddhima.myapplicationtest.bean.ActionList;

import java.util.ArrayList;

/**
 * Created by sunethe on 4/14/2015.
 */
public class MyExpandableListAdapter extends BaseExpandableListAdapter {
    private LayoutInflater inflater;
    //Initialize variables

    private int ActionListClickStatus = -1;
    private int ActionClickStatus = -1;
    private Context context;
    ArrayList<ActionList> listOfActionList;
    Resources res;


    public MyExpandableListAdapter(Context context, ArrayList<ActionList> listOfActionLists) {
        // Create Layout Inflator
        this.context = context;
        this.listOfActionList = listOfActionLists;
        res = context.getResources();
        inflater = LayoutInflater.from(context);
    }


    // This Function used to inflate parent rows view

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup actionListView) {
        System.out.println("getGroupView>>>>>>>>>>>>>>>>");
        final ActionList actionList = listOfActionList.get(groupPosition);

        // Inflate grouprow.xml file for parent rows
        convertView = inflater.inflate(R.layout.grouprow, actionListView, false);
        // Get grouprow.xml file elements and set values
        ((TextView) convertView.findViewById(R.id.text1)).setText(actionList.getName());

        final ImageView rightcheck = (ImageView) convertView.findViewById(R.id.rightcheck);

        // Get grouprow.xml file checkbox elements
         CheckBox checkbox = (CheckBox) convertView.findViewById(R.id.checkbox);


        checkbox.setOnCheckedChangeListener(new CheckUpdateListener(actionList, context,this));
//        checkbox.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                CheckBox cb = (CheckBox)v;
//                if (cb.isChecked()) {
//                    System.out.println("Checked<<<<<<<<<<<<>>>>>>>>>>>>>>>>>");
//                    rightcheck.setImageResource(R.drawable.rightcheck);
//                } else {
//                    System.out.println("UnChecked<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>");
//                    rightcheck.setImageResource(R.drawable.button_check);
//                }
//                actionList.setChecked(cb.isChecked());
//            }
//        });

//        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                actionList.setChecked(isChecked);
//
//            }
//        });

        return convertView;
    }

    private void updateGroupStatus(){

    }


    // This Function used to inflate child rows view
    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild,
                             View convertView, ViewGroup parentView) {
        System.out.println("getChildView>>>>>>>>>>");
        final ActionList parent = listOfActionList.get(groupPosition);
        final Action child = parent.getPhaseActions().get(childPosition);

        // Inflate childrow.xml file for child rows
        convertView = inflater.inflate(R.layout.childrow, parentView, false);
        CheckedTextView ctv = ((CheckedTextView) convertView.findViewById(R.id.actionName));
        ctv.setText(child.getName());
        ctv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckedTextView action = (CheckedTextView) v;
                if (action.isChecked()) {
                    action.setChecked(false);
                } else {
                    action.setChecked(true);
                }

                Toast.makeText(context, "ActionList : " + groupPosition + " Action : " + childPosition + "  : " + action.isChecked(),
                        Toast.LENGTH_LONG).show();

            }
        });

        return convertView;
    }


    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return listOfActionList.get(groupPosition).getPhaseActions().get(childPosition);
    }

    //Call when child row clicked
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        System.out.println("getChildId>>>>>>>>>>");
//        Toast.makeText(context, "ActionList : " + groupPosition + " Action : " + childPosition,
//                Toast.LENGTH_LONG).show();

        return childPosition;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        int size = 0;
        if (listOfActionList.get(groupPosition).getPhaseActions() != null)
            size = listOfActionList.get(groupPosition).getPhaseActions().size();
        return size;
    }


    @Override
    public Object getGroup(int groupPosition) {
        return listOfActionList.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return listOfActionList.size();
    }

    //Call when parent row clicked
    @Override
    public long getGroupId(int groupPosition) {
        System.out.println("getGroupId>>>>>>>>>>");
//        Toast.makeText(context, "ActionList:" + groupPosition + " is selected.", Toast.LENGTH_LONG).show();
        return groupPosition;
    }

    @Override
    public void notifyDataSetChanged() {
        // Refresh List rows
//        super.notifyDataSetChanged();
    }

    @Override
    public boolean isEmpty() {
        return ((listOfActionList == null) || listOfActionList.isEmpty());
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return true;
    }



}

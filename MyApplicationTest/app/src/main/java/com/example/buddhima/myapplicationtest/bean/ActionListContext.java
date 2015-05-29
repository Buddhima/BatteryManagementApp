package com.example.buddhima.myapplicationtest.bean;

import android.graphics.Point;
import android.support.v7.app.ActionBarActivity;
import android.widget.CheckedTextView;
import android.widget.LinearLayout;
import android.widget.Switch;


/**
 * Created by sunethe on 5/28/2015.
 */

import com.example.buddhima.myapplicationtest.Globals;
import com.example.buddhima.myapplicationtest.R;

import java.util.ArrayList;

public class ActionListContext {

    public ArrayList<String> listPhase1ActionName;
    public ArrayList<String> listPhase2ActionName;
    public ArrayList<ActionList> listContext;

    public LinearLayout layoutPhase1;
    public LinearLayout layoutPhase2;

    Switch sw1;
    Switch sw2;
    //status
    private boolean isPhase1Active;
    private boolean isPhase2Active;

    private int timeout;


    public ActionListContext() {

    }

    public ActionListContext(LinearLayout layoutPhase1, LinearLayout layoutPhase2, Switch sw1, Switch sw2) {
        listContext = new ArrayList<ActionList>();
        listPhase1ActionName = new ArrayList<String>();
        listPhase2ActionName = new ArrayList<String>();
        this.layoutPhase1 = layoutPhase1;
        this.layoutPhase2 = layoutPhase2;

        this.sw1 = sw1;
        this.sw2 = sw2;

        updateActionContext(layoutPhase1, layoutPhase2);
        setPhase1Active(sw1.isChecked());
        setPhase2Active(sw2.isChecked());

    }

    public void updateActionContext(LinearLayout layoutPhase1, LinearLayout layoutPhase2) {
        listContext.clear();
        listPhase1ActionName.clear();
        listPhase2ActionName.clear();

        layoutPhase1.getChildCount();

        for (int i = 0; i < layoutPhase1.getChildCount(); i++) {
            CheckedTextView ctv = (CheckedTextView) layoutPhase1.getChildAt(i);
            listPhase1ActionName.add(ctv.getText().toString());
        }

        layoutPhase2.getChildCount();

        for (int i = 0; i < layoutPhase2.getChildCount(); i++) {
            CheckedTextView ctv = (CheckedTextView) layoutPhase2.getChildAt(i);
            listPhase2ActionName.add(ctv.getText().toString());
        }

        listContext.add(new ActionList(1, listPhase1ActionName));
        listContext.add(new ActionList(2, listPhase2ActionName));

        updateActionStatusFromUI(layoutPhase1);
        updateActionStatusFromUI(layoutPhase2);
    }


    public void printActionContext() {
        for (int i = 0; i < listContext.size(); i++) {
            ActionList actionList = listContext.get(i);
            for (int j = 0; j < actionList.getSize(); j++) {
                Action action = actionList.getAction(j);
                System.out.println("Name : " + action.getName() + "Status: " + (action.isChecked() ? "Checked" : "NotChecked"));
            }
        }
    }

    public boolean setActionStatus(String actionName, boolean isChecked) {
        for (int i = 0; i < listContext.size(); i++) {
            ActionList actionList = listContext.get(i);
            for (int j = 0; j < actionList.getSize(); j++) {
                Action action = actionList.getAction(j);
                if (actionName.equals(action.getName())) {
                    action.setChecked(isChecked);

                    if (isChecked && actionName.equals("Screen Timeout"))
                        return true;

                    break;
                }
            }
        }
        printActionContext();
        return false;
    }

    public boolean getActionStatus(String actionName) {
        for (int i = 0; i < listContext.size(); i++) {
            ActionList actionList = listContext.get(i);
            for (int j = 0; j < actionList.getSize(); j++) {
                Action action = actionList.getAction(j);
                if (actionName.equals(action.getName())) {
                    return action.isChecked();
                }
            }
        }
        return false;
    }

    private void updateActionStatusFromUI(LinearLayout layoutPhase) {
        layoutPhase.getChildCount();
        for (int i = 0; i < layoutPhase.getChildCount(); i++) {
            CheckedTextView ctv = (CheckedTextView) layoutPhase.getChildAt(i);
            setActionStatus(ctv.getText().toString(), ctv.isChecked());
        }
    }


    private void enableDisablePhase(boolean isChecked, LinearLayout layoutPhase) {
        layoutPhase.getChildCount();
        for (int i = 0; i < layoutPhase.getChildCount(); i++) {
            CheckedTextView ctv = (CheckedTextView) layoutPhase.getChildAt(i);
            ctv.setEnabled(isChecked);
        }
    }

    private CheckedTextView getCheckedTextView(LinearLayout layoutPhase, String actionName) {
        layoutPhase.getChildCount();
        for (int i = 0; i < layoutPhase.getChildCount(); i++) {
            CheckedTextView ctv = (CheckedTextView) layoutPhase.getChildAt(i);
            if (actionName.equals(ctv.getText().toString())) {
                return ctv;
            }
        }
        return null;
    }

    public Point getPopupLocation() {
        int[] location = new int[2];
        CheckedTextView ctv = (CheckedTextView) getCheckedTextView(layoutPhase2, "Screen Timeout");

        // Get the x, y location and store it in the location[] array
        // location[0] = x, location[1] = y.
        if (ctv == null)
            return null;

        ctv.getLocationOnScreen(location);

        //Initialize the Point with x, and y positions
        Point p = new Point();
        p.x = location[0];
        p.y = location[1];

        return p;
    }


    //getters and setters
    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public boolean isPhase1Active() {
        return isPhase1Active;
    }

    public void setPhase1Active(boolean isPhase1Active) {
        this.isPhase1Active = isPhase1Active;
        enableDisablePhase(isPhase1Active, layoutPhase1);
    }

    public boolean isPhase2Active() {
        return isPhase2Active;
    }

    public void setPhase2Active(boolean isPhase2Active) {
        this.isPhase2Active = isPhase2Active;
        enableDisablePhase(isPhase2Active, layoutPhase2);
    }

    public void updateUI() {

        System.out.println("UPDATING <<<<<<<<<<<<<<<<<<");
        for (int i = 0; i < listContext.size(); i++) {
            ActionList actionList = listContext.get(i);
            for (int j = 0; j < actionList.getSize(); j++) {
                Action action = actionList.getAction(j);
                action.setChecked(Globals.arrayActionStatus[i][j]);     //update the context object

                System.out.println("NAME : " + action.getName() + "Status : " + action.isChecked());
                if (i == 0) {
                    getCheckedTextView(layoutPhase1, action.getName()).setChecked(Globals.arrayActionStatus[i][j]);
                } else if (i == 1) {
                    getCheckedTextView(layoutPhase2, action.getName()).setChecked(Globals.arrayActionStatus[i][j]);
                }

                System.out.println("STTUS GLOABL : "+ Globals.arrayActionStatus[i][j]);

            }
        }

        sw1.setChecked(Globals.isPhase1Active);
        sw2.setChecked(Globals.isPhase2Active);

        setPhase1Active(Globals.isPhase1Active);
        setPhase2Active(Globals.isPhase2Active);

        timeout = Globals.timeout;

//        updateActionContext(layoutPhase1, layoutPhase2);
//


    }


}

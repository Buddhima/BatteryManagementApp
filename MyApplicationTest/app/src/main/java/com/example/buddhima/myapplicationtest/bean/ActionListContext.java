package com.example.buddhima.myapplicationtest.bean;

import android.support.v7.app.ActionBarActivity;
import android.widget.CheckedTextView;
import android.widget.LinearLayout;


/**
 * Created by sunethe on 5/28/2015.
 */

import com.example.buddhima.myapplicationtest.R;

import java.util.ArrayList;

public class ActionListContext {

    ArrayList<String> listPhase1ActionName;
    ArrayList<String> listPhase2ActionName;
    ArrayList<ActionList> listContext;

    LinearLayout layoutPhase1;
    LinearLayout layoutPhase2;

    //status
    private boolean isPhase1Active;
    private boolean isPhase2Active;


    public ActionListContext(LinearLayout layoutPhase1, LinearLayout layoutPhase2) {
        System.out.println("ActionListContext<<<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        listContext = new ArrayList<ActionList>();
        listPhase1ActionName = new ArrayList<String>();
        listPhase2ActionName = new ArrayList<String>();
        this.layoutPhase1 = layoutPhase1;
        this.layoutPhase2 = layoutPhase2;

        updateActionContext(layoutPhase1, layoutPhase2);

    }

    public void updateActionContext(LinearLayout layoutPhase1, LinearLayout layoutPhase2) {
        System.out.println("updateActionContext<<<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
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
        System.out.println("printActionContext<<<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        for (int i = 0; i < listContext.size(); i++) {
            ActionList actionList = listContext.get(i);
            for (int j = 0; j < actionList.getSize(); j++) {
                Action action = actionList.getAction(j);
                System.out.println("Name : " + action.getName() + "Status: " + (action.isChecked() ? "Checked" : "NotChecked"));
            }
        }
    }

    public void setActionStatus(String actionName, boolean isChecked) {
        for (int i = 0; i < listContext.size(); i++) {
            ActionList actionList = listContext.get(i);
            for (int j = 0; j < actionList.getSize(); j++) {
                Action action = actionList.getAction(j);
                if (actionName.equals(action.getName())) {
                    action.setChecked(isChecked);
                    break;
                }
            }
        }

        printActionContext();
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

    private void updatePhaseStatus(){

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

    private void enableDisablePhase(boolean isChecked, LinearLayout layoutPhase){
        layoutPhase.getChildCount();
        for (int i = 0; i < layoutPhase.getChildCount(); i++) {
            CheckedTextView ctv = (CheckedTextView) layoutPhase.getChildAt(i);
            ctv.setEnabled(isChecked);
        }
    }
}

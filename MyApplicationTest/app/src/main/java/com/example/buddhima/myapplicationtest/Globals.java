package com.example.buddhima.myapplicationtest;

import android.content.ContentResolver;
import android.widget.CheckedTextView;

import com.example.buddhima.myapplicationtest.bean.Action;
import com.example.buddhima.myapplicationtest.bean.ActionList;
import com.example.buddhima.myapplicationtest.bean.ActionListContext;

/**
 * Created by sunethe on 5/29/2015.
 */
public class Globals {

    public  static boolean isAllEnabled;
    public static ActionListContext actionListContext = null;
    public static boolean isPhase1Active;
    public static boolean isPhase2Active;
    public static int timeout;
    public static boolean[][] arrayActionStatus;

    public static ContentResolver cResolver;

    private static int batteryLevel;

    public static void updateGlobalStatus(){
        if (actionListContext == null)
            return;

        System.out.println("updateGlobalStatus<<<<<<<<<<<<<<<<<<<<");

        arrayActionStatus = new boolean[actionListContext.listContext.size()][];
        for (int i = 0; i < actionListContext.listContext.size(); i++) {
            ActionList actionList = actionListContext.listContext.get(i);
            arrayActionStatus[i] = new boolean[actionList.getSize()];
            for (int j = 0; j < actionList.getSize(); j++) {
                Action action = actionList.getAction(j);
                arrayActionStatus[i][j] = action.isChecked();
            }
        }

        isPhase1Active = actionListContext.isPhase1Active();
        isPhase2Active = actionListContext.isPhase2Active();

        timeout = actionListContext.getTimeout();
    }

    public static int getBatteryLevel() {
//        return batteryLevel;
        return 49;
    }

    public static void setBatteryLevel(int batteryLevel) {
        Globals.batteryLevel = batteryLevel;
    }
}

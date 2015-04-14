package com.example.buddhima.myapplicationtest.bean;

import java.util.ArrayList;

/**
 * Created by sunethe on 4/14/2015.
 */
public class ActionList {
    private ArrayList<Action> phaseActions;
    private String[] actionArr;
    private String name;
    private int phase;
    private boolean checked;


    public ActionList(int phase, String[] actionArr) {
        this.actionArr = actionArr;
        this.phase = phase;
        this.name = "Phase" + phase;
        createPhaseActions();

    }

    private void createPhaseActions() {

        if (actionArr == null)
            return;

        phaseActions = new ArrayList<Action>();
        for (int i = 0; i < actionArr.length; i++) {
            System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<" + actionArr[i]);
            Action action = new Action(phase, i, actionArr[i]);
            phaseActions.add(action);

        }
    }


    public ArrayList<Action> getPhaseActions() {
        return phaseActions;
    }


    public String getName() {
        return name;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}

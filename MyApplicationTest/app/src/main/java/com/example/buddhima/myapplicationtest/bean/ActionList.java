package com.example.buddhima.myapplicationtest.bean;

import java.util.ArrayList;

/**
 * Created by sunethe on 4/14/2015.
 */
public class ActionList {
    private ArrayList<Action> phaseActions;
    private ArrayList<String> actionList;
    private String name;
    private int phase;
    private boolean checked;


    public ActionList(int phase, ArrayList<String> actionList) {
        this.actionList = actionList;
        this.phase = phase;
        this.name = "Phase" + phase;
        createPhaseActions();

    }

    private void createPhaseActions() {

        if (actionList == null)
            return;

        phaseActions = new ArrayList<Action>();
        for (int i = 0; i < actionList.size(); i++) {
            System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<" + actionList.get(i));
            Action action = new Action(phase, i, actionList.get(i));
            phaseActions.add(action);

        }
    }

    public int getSize(){
        return phaseActions.size();
    }

    public Action getAction(int i){
        return phaseActions.get(i);
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

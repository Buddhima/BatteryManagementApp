package com.example.buddhima.myapplicationtest.bean;

/**
 * Created by sunethe on 4/14/2015.
 */
public class Action {

    private int id;
    private int phase;
    private String elementId;
    private String name;
    private int status;
    private boolean checked;

    public Action(int phase, int id, String name) {
        this.phase = phase;
        this.id = id;
        this.name = name;
        this.elementId = "cb_" + phase + "_" + id;
        this.checked = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getPhase() {
        return phase;
    }

    public void setPhase(int phase) {
        this.phase = phase;
    }

    public String getElementId() {
        return elementId;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}

package com.bupc.checkme.core.model;

/**
 * Created by casjohnpaul on 1/9/2018.
 */

public class SimpleString implements Item {
    private String simpleString;


    public SimpleString(String simpleString) {
        this.simpleString = simpleString;
    }

    public String getSimpleString() {
        return simpleString;
    }

    public void setSimpleString(String simpleString) {
        this.simpleString = simpleString;
    }

    @Override
    public String toString() {
        return simpleString;
    }
}

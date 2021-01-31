package com.fsdm.wisd.stockmanagment;
/*
 **    *** Stock Managment ***
 **   Created by EL KHARROUBI HASSAN
 **   At Sunday January 2021 12H 47MIN
 */


public class Category {

    private int mId;
    private String mName;



    public Category(int id, String name) {
        mId = id;
        mName = name;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    @Override
    public String toString() {
        return getName();
    }
}

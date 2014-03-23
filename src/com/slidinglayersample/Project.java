package com.slidinglayersample;

import java.io.Serializable;

/**
 * @author bamboo
 * @since 3/22/14 7:54 AM
 */
public class Project implements Serializable {

    private String mName = "Project Name";

    private String mManager;

    private int mProgress = 0;

    public int getProgress() {
        return mProgress;
    }

    public void setProgress(int progress) {
        mProgress = progress;
    }

    public String getManager() {
        return mManager;
    }

    public void setManager(String manager) {
        mManager = manager;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }
}

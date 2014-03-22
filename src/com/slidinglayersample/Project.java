package com.slidinglayersample;

/**
 * @author bamboo
 * @since 3/22/14 7:54 AM
 */
public class Project {

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
}

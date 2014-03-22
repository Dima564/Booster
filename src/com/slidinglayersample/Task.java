package com.slidinglayersample;

/**
 * @author bamboo
 * @since 3/22/14 7:54 AM
 */
public class Task extends BasicTask {

    private long mStartDate;

    private long mDueDate;

    private boolean mDone;

    private boolean mConfirmed;

    private int mMaxValue;

    public long getStartDate() {
        return mStartDate;
    }

    public void setStartDate(long startDate) {
        mStartDate = startDate;
    }

    public long getDueDate() {
        return mDueDate;
    }

    public void setDueDate(long dueDate) {
        mDueDate = dueDate;
    }

    public boolean isDone() {
        return mDone;
    }

    public void setDone(boolean done) {
        mDone = done;
    }

    public boolean isConfirmed() {
        return mConfirmed;
    }

    public void setConfirmed(boolean confirmed) {
        mConfirmed = confirmed;
    }

    public int getMaxValue() {
        return mMaxValue;
    }

    public void setMaxValue(int maxValue) {
        mMaxValue = maxValue;
    }
}

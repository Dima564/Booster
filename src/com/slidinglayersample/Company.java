package com.slidinglayersample;

import java.io.Serializable;

/**
 * @author bamboo
 * @since 3/22/14 7:54 AM
 */
public class Company extends BasicTask implements Serializable {
    private String mCreatorId = "Andriy Bas";

    public String getCreatorId() {
        return mCreatorId;
    }

    public void setCreatorId(String creatorId) {
        mCreatorId = creatorId;
    }
}

package com.cougar.gcpnavigation.global;

import java.io.Serializable;

/**
 * Created by cougar0828 on 15/5/12.
 */
public class NewJourneyData implements Serializable {

    public int journeyCount;
    public String address;

    public void setJourneyCount(int journeyCount) {
        this.journeyCount = journeyCount;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getJourneyCount() {
        return journeyCount;
    }

    public String getAddress() {
        return address;
    }

    public NewJourneyData() {
        super();
    }
}

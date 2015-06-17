package com.cougar.gcpnavigation.global;

import java.io.Serializable;

/**
 * Created by cougar0828 on 15/5/22.
 */
public class JourneyListData implements Serializable {

    public String preCursor, agentGenome, picture, likesCount, name;
    public String createdTime, generation, genomes, jpurneyID, objectName;
    public String thumbnail, best, viessCount;

    public JourneyListData() {
        super();
    }
}

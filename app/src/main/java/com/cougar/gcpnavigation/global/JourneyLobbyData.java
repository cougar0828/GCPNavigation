package com.cougar.gcpnavigation.global;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Created by cougar0828 on 15/5/22.
 */
public class JourneyLobbyData implements Serializable {

    public String name, viewsCount, likesCount;
    public String journeyPic, accountPic;

    public JourneyLobbyData() {
       super();
    }
}

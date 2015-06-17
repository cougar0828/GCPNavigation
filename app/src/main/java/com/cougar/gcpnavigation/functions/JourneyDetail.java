package com.cougar.gcpnavigation.functions;

import android.app.Activity;
import android.os.Bundle;

import com.cougar.gcpnavigation.R;
import com.cougar.gcpnavigation.global.JourneyListData;
import com.cougar.gcpnavigation.global.JourneyLobbyData;
import com.cougar.gcpnavigation.global.Params;

/**
 * Created by cougar0828 on 15/5/22.
 */
public class JourneyDetail extends Activity {

    private JourneyLobbyData mSelectJourney;
//    private JourneyListData mSelectJourney;

    private final String TAG = "JourneyDetail";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.journeydetail);
        
        initData();
        initUI();
    }

    private void initData() {
       Bundle bundle = getIntent().getExtras();
       mSelectJourney = (JourneyLobbyData) bundle.get(Params.JOURNEY_DETAIL);
    }

    private void initUI() {


    }
}

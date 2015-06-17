package com.cougar.gcpnavigation.functions;


import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.cougar.gcpnavigation.R;
import com.cougar.gcpnavigation.interfaces.OnServerListener;
import com.gc.materialdesign.widgets.Dialog;

/**
 * Created by cougar0828 on 15/5/19.
 */
public class JourneyResult extends Activity implements OnServerListener {

    private EditText mJourneyName;
    private String journeyName;

    private boolean ifDoubleBack;

    private final String TAG = "JourneyResult";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.journeyresult);

        initData();
        initUI();
    }

    private void initData() {


    }

    private void initUI() {


    }

    @Override
    public void onBackPressed() {

        if (ifDoubleBack) {
            super.onBackPressed();
            return;
        }

        this.ifDoubleBack = true;
        setToast(getString(R.string.str_ifDoubleBack));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ifDoubleBack = false;
            }
        }, 2000);
        return;
    }

    public void toSaveJourney(View v) {


    }

    private void setToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onFinishListener(Object result) {

    }

    @Override
    public void onErrorListener(int cancelCode) {

    }

    @Override
    public void onConnectionFailureListener() {

    }
}

package com.cougar.gcpnavigation.functions;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cougar.gcpnavigation.R;
import com.cougar.gcpnavigation.global.Params;
import com.gc.materialdesign.views.Button;

/**
 * Created by cougar0828 on 15/5/28.
 */
public class HelpFeedback extends Fragment implements View.OnClickListener {

    private Button mCall;
    private final String TAG = "HelpFeedback";

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.helpfeedback, container, false);

        mCall = (Button) view.findViewById(R.id.callHelp);
        mCall.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.callHelp:
               callHelp();
                break;
        }
    }

    private void callHelp() {

        Log.d(TAG, "Ready to call");
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + getString(R.string.str_simonphone)));
        callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(callIntent);
    }
}

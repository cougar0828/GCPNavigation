package com.cougar.gcpnavigation.functions;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.cougar.gcpnavigation.R;
import com.cougar.gcpnavigation.adapter.NewJourneyAdapter;
import com.cougar.gcpnavigation.global.NewJourneyData;
import com.cougar.gcpnavigation.interfaces.OnServerListener;
import com.gc.materialdesign.views.Button;
import com.gc.materialdesign.views.ProgressBarIndeterminate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class NewJourney extends Fragment implements View.OnLongClickListener, OnServerListener, View.OnClickListener {

    private GoogleMap mGoogleMap;
    private MapView mMapView;
    private LatLng mLatlng;

    private List<NewJourneyData> mData = new ArrayList<NewJourneyData>();
    private NewJourneyData mSelectJourney;
    private NewJourneyAdapter mAdapter;

    private EditText mJourneyName;
    private String journeyName, selectAccount, accountToken, addressSet;
    private int journeyCount = 0;

    private Button mScheduleForMe;
    private RecyclerView mRecycleview;
    private LinearLayoutManager mLayoutManager;

    private ProgressBarIndeterminate mProgress;
    private SharedPreferences mShare;
    private final String DATA = "DATA";
    private final String ACCOUNT = "ACCOUNT";
    private final String TOKEN = "TOKEN";

    private final String TAG = "NewJourney";

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initData();
    }

    private void initData() {

        mShare = getActivity().getSharedPreferences(DATA, 0);
        selectAccount = mShare.getString(ACCOUNT, "");
        accountToken = mShare.getString(TOKEN, "");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // for fragment
        View view = inflater.inflate(R.layout.newjourney, container, false);

        // init ui
        mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        mScheduleForMe = (Button) view.findViewById(R.id.scheduleForMe);
        mScheduleForMe.setOnClickListener(this);

        // prepare recycleview
        mRecycleview = (RecyclerView) view.findViewById(R.id.journeyCheckList);
        mRecycleview.setLayoutManager(mLayoutManager);
        mRecycleview.setItemAnimator(new DefaultItemAnimator());
        mRecycleview.setHasFixedSize(true);
        mAdapter = new NewJourneyAdapter(getActivity());

        // inti map
        mMapView = (MapView) view.findViewById(R.id.googleMap);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();

        MapsInitializer.initialize(getActivity());

        mGoogleMap = mMapView.getMap();
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mGoogleMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {

                mLatlng = latLng;
                addJourney();
            }
        });

        return view;
    }

    private void addJourney() {

        // convert to address
        String address = "";
        Geocoder gc = new Geocoder(getActivity(), Locale.getDefault());
        mSelectJourney = new NewJourneyData();

        try {
            List<Address> addressList = gc.getFromLocation(mLatlng.latitude, mLatlng.longitude, 1);

            if (gc.isPresent()) {

                address = addressList.get(0).getAddressLine(0);
                journeyCount++;
                Log.d(TAG, "Count : " + journeyCount);
                Log.d(TAG, "Address : " + address);

                // add info marker
                final Marker marker = mGoogleMap.addMarker(new MarkerOptions()
                        .position(mLatlng).title(journeyCount + ". " + address).snippet(""));

                // move to marker
                mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mLatlng, 11));

                // set adapter onlongclick with adapter
                mAdapter.setOnLongClickListener(new NewJourneyAdapter.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view, int position) {

                        mAdapter.remove(position);
                        marker.remove();
                        journeyCount--;

                        if (journeyCount == 0) {
                            mGoogleMap.clear();
                        }
                        return true;
                    }
                });

                if (marker != null) {
                    // add journey item
                    mSelectJourney.setJourneyCount(journeyCount);
                    mSelectJourney.setAddress(address);
                    mData.add(journeyCount - 1, mSelectJourney);


                    // add to string-array
                    if (journeyCount == 1) {
                        addressSet = "\"" + address + "\"" + ",";
                    } else if (journeyCount > 1 && journeyCount < 5) {
                        addressSet = addressSet + "\"" + address + "\"" + ",";
                    } else {
                        addressSet = addressSet + "\"" + address + "\"";
                        Log.d(TAG, "Final addressSet : " + addressSet);
                    }

                    // recycleview refresh
                    mAdapter.setmData(mData);
                    mAdapter.notifyDataSetChanged();
                    mRecycleview.setAdapter(mAdapter);
                }

            } else {
                setToast(getString(R.string.str_noAddress));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // check address legal
        if (journeyCount > 1) {

            Log.d(TAG, "Journey count : " + journeyCount);
            String beforeAdd = mData.get(journeyCount - 2).getAddress();
            Log.d(TAG, "Before : " + beforeAdd);
            String afterAdd = mData.get(journeyCount - 1).getAddress();
            Log.d(TAG, "After : " + afterAdd);

            // Check two addresses can be routed.
            // If yes, its legal. then start insert
        }
    }

    private void setToast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onLongClick(View v) {
        return false;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.scheduleForMe:
                toSchedule();
                break;
        }
    }

    private void toSchedule() {

        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.inflate_newjourney, null);
        mJourneyName = (EditText) view.findViewById(R.id.enterJourneyName);

        new AlertDialog.Builder(getActivity())
                .setTitle(getString(R.string.str_editJourneyNameTitle))
                .setView(view)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String journeyName = (mJourneyName.getText()).toString()
                                .trim();

                        if (!journeyName.equals("")) {
                            startSchedule(journeyName);
                            dialog.dismiss();
                        } else {
                            showRegisterEmpty();
                        }
                    }
                })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                            }
                        }).show();
    }

    private void startSchedule(String journeyName) {

        //header : token
        //file_name : to make journeyname.txt
        //body : transfer journeyname.txt's content to binaryString, then to base64
        //content_type : fix text/plain

        InsertJourneyAsync async = new InsertJourneyAsync(getActivity());
        async.setmListener(this);
        async.setToken(accountToken);
        async.setJourneyName(journeyName);
        async.setmAddressSet(addressSet);

        Log.d(TAG, "Journey name : " + journeyName);
        Log.d(TAG, "Token : " + accountToken);
        Log.d(TAG, "Address set : " + addressSet);

        async.execute(0);
    }

    private void showRegisterEmpty() {
        setToast(getString(R.string.str_noEnterJourneyName));
    }

    @Override
    public void onFinishListener(Object result) {
        setToast(getString(R.string.str_success));

        //find a way to journeyList
        Fragment goJourneyList = new JourneyList();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        transaction.replace(R.id.container, goJourneyList);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onErrorListener(int cancelCode) {
        switch (cancelCode) {

            case -1:
                setToast(getString(R.string.str_noData));
                break;
            case -2:
                setToast(getString(R.string.str_paramError));
                break;
        }
    }

    @Override
    public void onConnectionFailureListener() {
        setToast(getString(R.string.str_connectionFailure));
    }
}

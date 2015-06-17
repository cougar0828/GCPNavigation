package com.cougar.gcpnavigation.functions;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.cougar.gcpnavigation.R;
import com.cougar.gcpnavigation.adapter.JourneyLobbyAdapter;
import com.cougar.gcpnavigation.global.JourneyLobbyData;
import com.cougar.gcpnavigation.global.Params;
import com.cougar.gcpnavigation.interfaces.OnServerListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class JourneyList extends Fragment implements OnServerListener {

    private RecyclerView mRecycleView;
    private LinearLayoutManager mLayoutManager;
    private JourneyLobbyAdapter mAdapter;

    private List<JourneyLobbyData> mData = new ArrayList<JourneyLobbyData>();
    private JourneyLobbyData mSelectJourney;

    private String account, token;

    private SharedPreferences mShare;
    private final String DATA = "DATA";
    private final String ACCOUNT = "ACCOUNT";
    private final String TOKEN = "TOKEN";

    private final String TAG = "JourneyList";


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void initData() {

        mShare = getActivity().getSharedPreferences(DATA, 0);
        account = mShare.getString(ACCOUNT, "");
        token = mShare.getString(TOKEN, "");
    }

    private void getJourney() {

        GetPersonalJourAsync async = new GetPersonalJourAsync(getActivity());
        async.setmListener(this);
        async.setAccountToken(token);
        async.execute(0);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        initData();
        getJourney();

        View view = inflater.inflate(R.layout.journeylist, container, false);
        mRecycleView = (RecyclerView) view.findViewById(R.id.journeyList);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecycleView.setLayoutManager(mLayoutManager);
        mRecycleView.setHasFixedSize(true);

        mAdapter = new JourneyLobbyAdapter(getActivity());

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        getJourney();
    }

    private void setToast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onFinishListener(Object result) {

        mData = (List<JourneyLobbyData>) result;

        mAdapter.setmData(mData);
        mAdapter.notifyDataSetChanged();
        mRecycleView.setAdapter(mAdapter);

        //set adapter onitemclick with adapter
        mAdapter.setOnItemClickListener(new JourneyLobbyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                mSelectJourney = mData.get(position);

                Intent intent = new Intent();
                intent.setClass(getActivity(), JourneyDetail.class);

                Bundle bundle = new Bundle();
                bundle.putSerializable(Params.JOURNEY_DETAIL, (Serializable)mSelectJourney);

                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
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

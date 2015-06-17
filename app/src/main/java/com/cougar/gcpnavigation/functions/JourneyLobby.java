package com.cougar.gcpnavigation.functions;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cougar.gcpnavigation.R;
import com.cougar.gcpnavigation.adapter.JourneyLobbyAdapter;
import com.cougar.gcpnavigation.global.JourneyLobbyData;
import com.cougar.gcpnavigation.global.Params;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cougar0828 on 15/5/21.
 */
public class JourneyLobby extends Fragment {

    private List<JourneyLobbyData> mData = new ArrayList<JourneyLobbyData>();

    private RecyclerView mRecycleView;
    private LinearLayoutManager mLayoutManager;
    private JourneyLobbyAdapter mAdapter;

    private final String TAG = "JourneyLobby";

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        initData();

        View view = inflater.inflate(R.layout.journeylobby, container, false);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        mRecycleView = (RecyclerView) view.findViewById(R.id.journeyLobby);
        mRecycleView.setLayoutManager(mLayoutManager);
        mRecycleView.setItemAnimator(new DefaultItemAnimator());
        mRecycleView.setHasFixedSize(true);

        mAdapter = new JourneyLobbyAdapter(getActivity());
        mAdapter.setmData(mData);
        mAdapter.notifyDataSetChanged();
        mRecycleView.setAdapter(mAdapter);

        // set adapter onitemclick with adapter
        mAdapter.setOnItemClickListener(new JourneyLobbyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }
        });
        return view;
    }

    private void initData() {
        Bundle bundle = getActivity().getIntent().getExtras();
        mData = (List<JourneyLobbyData>) bundle.get(Params.JOURNEY_LOBBY);
    }
}

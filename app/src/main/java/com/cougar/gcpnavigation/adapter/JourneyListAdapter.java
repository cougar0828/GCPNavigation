package com.cougar.gcpnavigation.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by cougar0828 on 15/5/22.
 */
public class JourneyListAdapter extends RecyclerView.Adapter<JourneyListAdapter.JourneyListHolder> {


    @Override
    public JourneyListAdapter.JourneyListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(JourneyListAdapter.JourneyListHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class JourneyListHolder extends RecyclerView.ViewHolder{

        public JourneyListHolder(View itemView) {
            super(itemView);
        }
    }
}

package com.cougar.gcpnavigation.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.cougar.gcpnavigation.R;
import com.cougar.gcpnavigation.functions.GetPicAsync;
import com.cougar.gcpnavigation.global.JourneyLobbyData;

import java.util.List;

/**
 * Created by cougar0828 on 15/5/22.
 */
public class JourneyLobbyAdapter extends RecyclerView.Adapter<JourneyLobbyAdapter.JourneyLobbyHolder> {

    private Context mContext;
    private List<JourneyLobbyData> mData;
    private OnItemClickListener mItemClickListener;

    private final String TAG = "JourneyLobbyAdapter";

    public JourneyLobbyAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setmData(List<JourneyLobbyData> mData) {
        this.mData = mData;
        Log.d(TAG, "Size : " + mData.size());
    }

    @Override
    public JourneyLobbyAdapter.JourneyLobbyHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.element_journeylobby, viewGroup, false);

        return new JourneyLobbyHolder(itemView);
    }

    @Override
    public void onBindViewHolder(JourneyLobbyAdapter.JourneyLobbyHolder holder, int position) {

        holder.mName.setText(mData.get(position).name);
        holder.mViewsCount.setText(mData.get(position).viewsCount);
        holder.mLikesCount.setText(mData.get(position).likesCount);

        if (holder.mJourneyPic != null) {
            new GetPicAsync(holder.mJourneyPic).execute(mData.get(position).journeyPic);
        }

        if (holder.mAccoutPic != null) {
            new GetPicAsync(holder.mAccoutPic).execute(mData.get(position).accountPic);
        }
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public class JourneyLobbyHolder extends RecyclerView.ViewHolder {

        public TextView mName, mViewsCount, mLikesCount;
        public ImageView mJourneyPic, mAccoutPic;

        public JourneyLobbyHolder(View itemView) {
            super(itemView);

            mName = (TextView) itemView.findViewById(R.id.accountName);
            mViewsCount = (TextView) itemView.findViewById(R.id.viewsCount);
            mLikesCount = (TextView) itemView.findViewById(R.id.likesCount);
            mJourneyPic = (ImageView) itemView.findViewById(R.id.journeyPic);
            mAccoutPic = (ImageView) itemView.findViewById(R.id.accountPic);

            //set recycleview onitemclicklistener - 1
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mItemClickListener.onItemClick(view, getPosition());
                }
            });
        }
    }

    //set recycleview onitemclicklistener - 2
    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    //set recycleview onitemclicklistener - 3
    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }
}

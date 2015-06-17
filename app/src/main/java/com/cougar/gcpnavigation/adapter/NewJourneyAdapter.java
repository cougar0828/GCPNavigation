package com.cougar.gcpnavigation.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cougar.gcpnavigation.R;
import com.cougar.gcpnavigation.global.NewJourneyData;

import java.util.List;

/**
 * Created by cougar0828 on 15/5/18.
 */
public class NewJourneyAdapter extends RecyclerView.Adapter<NewJourneyAdapter.NewJourneyHolder> {

    private Context mContext;
    private List<NewJourneyData> mData;
    private OnLongClickListener mLongClickListener;

    private final String TAG = "NewJourneyAdapter";

    public NewJourneyAdapter(Context context) {
        this.mContext = context;
    }

    public void setmData(List<NewJourneyData> mData) {
        this.mData = mData;
        Log.d(TAG, "Size : " + mData.size());
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public void onBindViewHolder(NewJourneyHolder holder, int position) {

        holder.mNum.setText(Integer.toString(mData.get(position).getJourneyCount()) + ".");
        holder.mAddress.setText(mData.get(position).address);
    }

    @Override
    public NewJourneyHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.element_newjourney, viewGroup, false);

        return new NewJourneyHolder(itemView);
    }

    public class NewJourneyHolder extends RecyclerView.ViewHolder {

        public TextView mNum, mAddress;
        public ImageView mChecker;
//      public ProgressBarIndeterminate mProgress;

        public NewJourneyHolder(View itemView) {
            super(itemView);

            mNum = (TextView) itemView.findViewById(R.id.journeyNum);
            mAddress = (TextView) itemView.findViewById(R.id.journeyAddress);
//          mProgress = (ProgressBarIndeterminate) itemView.findViewById(R.id.journeyCheck);
            mChecker = (ImageView) itemView.findViewById(R.id.journeyOK);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {

                    if (mLongClickListener != null) {
                        return mLongClickListener.onLongClick(view, getPosition());
                    }
                    return false;
                }
            });
        }
    }

    //remove data into recycleview
    public void remove(int position) {
        mData.remove(position);
        notifyItemRemoved(position);
    }

    public interface OnLongClickListener {
        public boolean onLongClick(View view, int position);
    }

    public void setOnLongClickListener(final OnLongClickListener mLongClickListener) {
        this.mLongClickListener = mLongClickListener;
    }
}

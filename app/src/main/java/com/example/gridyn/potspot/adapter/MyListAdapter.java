package com.example.gridyn.potspot.adapter;

import android.app.FragmentManager;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gridyn.potspot.R;
import com.example.gridyn.potspot.Spot;

import java.util.List;

public class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.Holder>{


    private final List<Spot> mSpotList;
    private final Context mContext;
    private final FragmentManager mFragmentManager;

    public MyListAdapter(List<Spot> spotList, Context context, FragmentManager fragmentManager) {
        mSpotList = spotList;
        mContext = context;
        mFragmentManager = fragmentManager;

    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.item_my_list, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mSpotList.size();
    }

    public static class Holder extends RecyclerView.ViewHolder {

        public Holder(View itemView) {
            super(itemView);
        }
    }
}

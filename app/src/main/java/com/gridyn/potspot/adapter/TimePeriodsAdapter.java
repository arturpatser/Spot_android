package com.gridyn.potspot.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.gridyn.potspot.model.Available;

import java.util.ArrayList;

/**
 * Created by dmytro_vodnik on 8/23/16.
 * working on potspot project
 */
public class TimePeriodsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_FOOTER = 1;
    private static final int TYPE_TIME_PERIOD = 0;
    Context context;
    LayoutInflater layoutInflater;
    ArrayList<Available> availableArrayList;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {

        return availableArrayList.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {

        if (position == availableArrayList.size() + 1)
            return TYPE_FOOTER;

        return TYPE_TIME_PERIOD;
    }
}

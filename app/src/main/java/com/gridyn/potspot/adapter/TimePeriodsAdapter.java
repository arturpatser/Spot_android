package com.gridyn.potspot.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.gridyn.potspot.R;
import com.gridyn.potspot.model.Available;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

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

    public TimePeriodsAdapter(Context context) {

        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        availableArrayList = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType) {

            case TYPE_FOOTER:

                View view = layoutInflater.inflate(R.layout.add_time_period, parent, false);

                return new Footer(view);

            case TYPE_TIME_PERIOD:

                View time = layoutInflater.inflate(R.layout.item_time_period, parent, false);

                return new TimePeriod(time);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof Footer) {

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    addItem(new Available());
                }
            });
        }

        if (holder instanceof TimePeriod) {

            if (position != 0)
            ((TimePeriod) holder).remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    removeItem(position);
                }
            });
        }
    }

    private void removeItem(int position) {

        availableArrayList.remove(position);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {

        return availableArrayList.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {

        if (position == availableArrayList.size())
            return TYPE_FOOTER;

        return TYPE_TIME_PERIOD;
    }

    public void addItem(Available available) {

        availableArrayList.add(available);
        notifyItemInserted(availableArrayList.size());
    }

    private class Footer extends RecyclerView.ViewHolder {
        public Footer(View view) {
            super(view);
        }
    }

    public class TimePeriod extends RecyclerView.ViewHolder {

        @BindView(R.id.btn_sunday)
         CheckBox mSunday;
        @BindView(R.id.btn_monday)
         CheckBox mMonday;
        @BindView(R.id.btn_tuesday)
         CheckBox mTuesday;
        @BindView(R.id.btn_wednesday)
         CheckBox mWednesday;
        @BindView(R.id.btn_thursday)
         CheckBox mThursday;
        @BindView(R.id.btn_friday)
         CheckBox mFriday;
        @BindView(R.id.btn_saturday)
         CheckBox mSaturday;
        @BindView(R.id.listing_setting_time_from)
         TextView mTimeFrom;
        @BindView(R.id.listing_setting_time_to)
         TextView mTimeTo;
        @BindView(R.id.remove)
        ImageView remove;

        public TimePeriod(View time) {
            super(time);

            ButterKnife.bind(this, time);
        }
    }
}

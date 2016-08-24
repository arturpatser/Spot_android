package com.gridyn.potspot.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.gridyn.potspot.R;
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
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof Footer) {

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    addItem(new Available());
                }
            });
        }

        if (holder instanceof TimePeriod) {


        }
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

    private class TimePeriod extends RecyclerView.ViewHolder {

        private CheckBox mSunday;
        private CheckBox mMonday;
        private CheckBox mTuesday;
        private CheckBox mWednesday;
        private CheckBox mThursday;
        private CheckBox mFriday;
        private CheckBox mSaturday;

        private TextView mTimeFrom;
        private TextView mTimeTo;

        public TimePeriod(View time) {
            super(time);

            mSunday = (CheckBox) time.findViewById(R.id.btn_sunday);
            mMonday = (CheckBox) time.findViewById(R.id.btn_monday);
            mTuesday = (CheckBox) time.findViewById(R.id.btn_tuesday);
            mWednesday = (CheckBox) time.findViewById(R.id.btn_wednesday);
            mThursday = (CheckBox) time.findViewById(R.id.btn_thursday);
            mFriday = (CheckBox) time.findViewById(R.id.btn_friday);
            mSaturday = (CheckBox) time.findViewById(R.id.btn_saturday);
            mTimeFrom = (TextView) time.findViewById(R.id.listing_setting_time_from);
            mTimeTo = (TextView) time.findViewById(R.id.listing_setting_time_to);
        }
    }
}

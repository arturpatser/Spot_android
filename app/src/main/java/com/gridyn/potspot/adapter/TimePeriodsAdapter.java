package com.gridyn.potspot.adapter;

import android.app.FragmentManager;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.gridyn.potspot.R;
import com.gridyn.potspot.model.Available;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dmytro_vodnik on 8/23/16.
 * working on potspot project
 */
public class TimePeriodsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_FOOTER = 1;
    private static final int TYPE_TIME_PERIOD = 0;
    private final FragmentManager fragmentManager;
    Context context;
    LayoutInflater layoutInflater;
    ArrayList<Available> availableArrayList;

    public TimePeriodsAdapter(Context context, FragmentManager fragmentManager) {

        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        availableArrayList = new ArrayList<>();
        this.fragmentManager = fragmentManager;
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
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof Footer) {

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    addItem(new Available());
                }
            });
        }

        if (holder instanceof TimePeriod) {

            final Available available = getItem(position);

            if (position != 0)
            ((TimePeriod) holder).remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    removeItem(position);
                }
            });

            ((TimePeriod) holder).availability.setText(context.getString(R.string.available_rules) +
                    " #" + (position + 1));

            ((TimePeriod) holder).timeFromL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final Calendar calendar = Calendar.getInstance();
                    TimePickerDialog.OnTimeSetListener callback = new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
                            ((TimePeriod) holder).mTimeFrom.setText(forViewTime(hourOfDay, minute));

                            available.time[0] = modifyTime(hourOfDay, minute);
                        }
                    };
                    final TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(
                            callback, calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE), false
                    );
                    timePickerDialog.setAccentColor(context.getResources().getColor(R.color.mainRed));
                    timePickerDialog.show(fragmentManager, "DatePickerDialog");
                }
            });

            ((TimePeriod) holder).timeToL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final Calendar calendar = Calendar.getInstance();
                    TimePickerDialog.OnTimeSetListener callback = new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {

                            ((TimePeriod) holder).mTimeTo.setText(forViewTime(hourOfDay, minute));

                            available.time[1] = modifyTime(hourOfDay, minute);
                        }
                    };
                    final TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(
                            callback, calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE), false
                    );
                    timePickerDialog.setAccentColor(context.getResources().getColor(R.color.mainRed));
                    timePickerDialog.show(fragmentManager, "DatePickerDialog");
                }
            });

            if (available.days != null) {

                ((TimePeriod) holder).mTimeFrom.setText(unWrapTime(available.time[0]));
                ((TimePeriod) holder).mTimeTo.setText(unWrapTime(available.time[1]));

                for (String day  : available.days) {

                    switch (day) {

                        case "Sunday":
                            ((TimePeriod) holder).mSunday.setChecked(true);
                            break;
                        case "Monday":
                            ((TimePeriod) holder).mMonday.setChecked(true);
                            break;
                        case "Tuesday":
                            ((TimePeriod) holder).mTuesday.setChecked(true);
                            break;
                        case "Wednesday":
                            ((TimePeriod) holder).mWednesday.setChecked(true);
                            break;
                        case "Thursday":
                            ((TimePeriod) holder).mThursday.setChecked(true);
                            break;
                        case "Friday":
                            ((TimePeriod) holder).mFriday.setChecked(true);
                            break;
                        case "Saturday":
                            ((TimePeriod) holder).mSaturday.setChecked(true);
                            break;
                    }
                }
            }
        }
    }

    private String unWrapTime(String s) {

        String time = "";

        if (s.length() == 3)
            time = "0" + s;

        time = time.substring(0,2) + ":" + time.substring(2,4);

        return time;
    }

    private String modifyTime(int hourOfDay, int minute) {

        String hourString = "" +hourOfDay;

        String minuteSting;
        if (minute < 10)
            minuteSting = "0" + minute;
        else
            minuteSting = "" +minute;

        return hourString + minuteSting;
    }

    private String forViewTime(int hourOfDay, int minute) {

        String hourString;
        if (hourOfDay < 10)
            hourString = "0" + hourOfDay;
        else
            hourString = "" +hourOfDay;

        String minuteSting;
        if (minute < 10)
            minuteSting = "0" + minute;
        else
            minuteSting = "" +minute;

        return hourString + ":" + minuteSting;
    }

    private Available getItem(int position) {

        return availableArrayList.get(position);
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

    public List<Available> getItems() {
        return availableArrayList;
    }

    public void addItems(ArrayList<Available> oldDays) {

        availableArrayList.addAll(oldDays);
        notifyDataSetChanged();
    }

    private class Footer extends RecyclerView.ViewHolder {
        public Footer(View view) {
            super(view);
        }
    }

    public class TimePeriod extends RecyclerView.ViewHolder {

        @BindView(R.id.btn_sunday)
        public CheckBox mSunday;
        @BindView(R.id.btn_monday)
         public CheckBox mMonday;
        @BindView(R.id.btn_tuesday)
         public CheckBox mTuesday;
        @BindView(R.id.btn_wednesday)
         public CheckBox mWednesday;
        @BindView(R.id.btn_thursday)
         public CheckBox mThursday;
        @BindView(R.id.btn_friday)
         public CheckBox mFriday;
        @BindView(R.id.btn_saturday)
         public CheckBox mSaturday;
        @BindView(R.id.listing_setting_time_from)
         public TextView mTimeFrom;
        @BindView(R.id.listing_setting_time_to)
         public TextView mTimeTo;
        @BindView(R.id.remove)
        public ImageView remove;
        @BindView(R.id.availability)
        public TextView availability;
        @BindView(R.id.time_from_layout)
        public FrameLayout timeFromL;
        @BindView(R.id.time_to_layout)
        public FrameLayout timeToL;

        public TimePeriod(View time) {
            super(time);

            ButterKnife.bind(this, time);
        }
    }
}

package com.gridyn.potspot.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.gridyn.potspot.Constant;
import com.gridyn.potspot.R;
import com.gridyn.potspot.adapter.TimePeriodsAdapter;
import com.gridyn.potspot.model.Available;
import com.gridyn.potspot.service.SpotService;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

public class ListingSettingActivityNew extends AppCompatActivity {

    private String mHourTo;
    private String mHourFrom;
    private String mMinuteTo;
    private String mMinuteFrom;

    private SpotService mService;

    TimePeriodsAdapter adapter;

    @BindView(R.id.time_periods)
    RecyclerView timePeriods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listing_setting_new);

        ButterKnife.bind(this);

        adapter = new TimePeriodsAdapter(this);

        timePeriods.setAdapter(adapter);
        timePeriods.setLayoutManager(new LinearLayoutManager(this));

        adapter.addItem(new Available());

        initToolbar();
        initRetrofit();
    }

    private void initRetrofit() {
        final Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constant.BASE_URL)
                .build();
        mService = retrofit.create(SpotService.class);
    }

    private void initToolbar() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.list_set_toolbar);
        toolbar.setTitle("");
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

//    public void onClickTimeFrom(View view) {
//        final Calendar calendar = Calendar.getInstance();
//        TimePickerDialog.OnTimeSetListener callback = new TimePickerDialog.OnTimeSetListener() {
//            @Override
//            public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
//                mHourFrom = String.valueOf(hourOfDay);
//                mMinuteFrom = String.valueOf(minute);
//                mTimeFrom.setText(mHourFrom + ":" + mMinuteFrom);
//            }
//        };
//        final TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(
//                callback, calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE), false
//        );
//        timePickerDialog.setAccentColor(getResources().getColor(R.color.mainRed));
//        timePickerDialog.show(getFragmentManager(), "DatePickerDialog");
//    }
//
//    public void onClickTimeTo(View view) {
//        final Calendar calendar = Calendar.getInstance();
//        TimePickerDialog.OnTimeSetListener callback = new TimePickerDialog.OnTimeSetListener() {
//            @Override
//            public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
//                mHourTo = String.valueOf(hourOfDay);
//                mMinuteTo = String.valueOf(minute);
//                mTimeTo.setText(mHourTo + ":" + mMinuteTo);
//            }
//        };
//        final TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(
//                callback, calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE), false
//        );
//        timePickerDialog.setAccentColor(getResources().getColor(R.color.mainRed));
//        timePickerDialog.show(getFragmentManager(), "DatePickerDialog");
//    }
//
//    public void onClickSaveCreateSpot(final View view) {
//        view.setVisibility(View.INVISIBLE);
//        final Intent intent = new Intent(this, MySpotsActivity.class);
//        Bundle extra = getIntent().getExtras();
//        CreateSpotQuery query = new CreateSpotQuery();
//        query.token = Person.getToken();
//        query.name = extra.getString("title");
//        if (!extra.getString("description").isEmpty()) {
//            query.about = extra.getString("description");
//        }
//        query.address = extra.getString("address");
//        query.price = Integer.parseInt(extra.getString("price")) * 100;
//
//        if (!extra.getString("maxGuests").isEmpty()) {
//            query.maxGuests = Integer.parseInt(extra.getString("maxGuests"));
//        }
//
//        if (!extra.getString("listingType").isEmpty()) {
//            query.type = extra.getString("listingType");
//        }
//
//        if (!extra.getString("tobacco").isEmpty()) {
//            query.badges.add(extra.getString("tobacco"));
//        }
//
//        if (!extra.getString("heated").isEmpty()) {
//            query.badges.add(extra.getString("heated"));
//        }
//
//        if (!extra.getString("handicap").isEmpty()) {
//            query.badges.add(extra.getString("handicap"));
//        }
//
//        query.days = getDays();
//        if (query.days.isEmpty()) {
//            Snackbar.make(findViewById(android.R.id.content), "Set days please", Snackbar.LENGTH_SHORT).show();
//            view.setVisibility(View.VISIBLE);
//            return;
//        }
//
//        if (mHourFrom != null && mMinuteFrom != null && mHourTo != null && mMinuteTo != null) {
////            query.time[0] = Double.parseDouble(mHourFrom) + (Double.parseDouble(mMinuteFrom) / 100);
////            query.time[1] = Double.parseDouble(mHourTo) + (Double.parseDouble(mMinuteTo) / 100);
//            query.time[0] = Integer.parseInt(mHourFrom + mMinuteFrom);
//            query.time[1] = Integer.parseInt(mHourTo + mMinuteTo);
//
//        } else {
//            Snackbar.make(findViewById(android.R.id.content), "Set time please", Snackbar.LENGTH_SHORT).show();
//            view.setVisibility(View.VISIBLE);
//            return;
//        }
//
//        Gson gson = new GsonBuilder().setPrettyPrinting().create();
//        Log.i(Constant.LOG, gson.toJson(query));
//
//        Call<SpotCreateResponse> call = mService.createSpot(query);
//        call.enqueue(new Callback<SpotCreateResponse>() {
//            @Override
//            public void onResponse(final Response<SpotCreateResponse> response, Retrofit retrofit) {
//                Log.i(Constant.LOG, new Gson().toJson(response.body()));
//                if (response.body().success) {
//                    Log.i(Constant.LOG, String.valueOf(response.code()));
//                    Log.i(Constant.LOG, "listing setting success true");
////                    intent.putExtra("id", response);
//                    startActivity(intent);
//                } else {
//                    Log.i(Constant.LOG, String.valueOf(response.code()));
//                    Snackbar.make(findViewById(android.R.id.content), "You already have the spot", Snackbar.LENGTH_INDEFINITE)
//                            .setAction("go to spot", new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
////                                    intent.putExtra("id", response);
//                                    startActivity(intent);
//                                }
//                            })
//                            .setActionTextColor(getResources().getColor(R.color.mainRed))
//                            .show();
//                }
//            }
//
//            @Override
//            public void onFailure(Throwable t) {
//                Snackbar.make(findViewById(android.R.id.content), Constant.CONNECTION_ERROR, Snackbar.LENGTH_SHORT).show();
//                view.setVisibility(View.VISIBLE);
//            }
//        });
//    }
//
//    private List<String> getDays() {
//        final List<String> days = new ArrayList<>();
//
//        if (mSunday.isChecked()) {
//            days.add("Sunday");
//        }
//
//        if (mMonday.isChecked()) {
//            days.add("Monday");
//        }
//
//        if (mTuesday.isChecked()) {
//            days.add("Tuesday");
//        }
//
//        if (mWednesday.isChecked()) {
//            days.add("Wednesday");
//        }
//
//        if (mThursday.isChecked()) {
//            days.add("Thursday");
//        }
//
//        if (mFriday.isChecked()) {
//            days.add("Friday");
//        }
//
//        if (mSaturday.isChecked()) {
//            days.add("Saturday");
//        }
//
//        return days;
//    }
}

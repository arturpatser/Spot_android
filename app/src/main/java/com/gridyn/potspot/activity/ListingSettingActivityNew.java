package com.gridyn.potspot.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.gridyn.potspot.Constant;
import com.gridyn.potspot.Person;
import com.gridyn.potspot.R;
import com.gridyn.potspot.adapter.TimePeriodsAdapter;
import com.gridyn.potspot.model.Available;
import com.gridyn.potspot.query.CreateSpotQuery;
import com.gridyn.potspot.response.SpotCreateResponse;
import com.gridyn.potspot.service.SpotService;
import com.gridyn.potspot.utils.SharedPrefsUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class ListingSettingActivityNew extends AppCompatActivity {

    private static final String TAG = ListingSettingActivityNew.class.getName();

    private SpotService mService;

    TimePeriodsAdapter adapter;

    @BindView(R.id.time_periods)
    RecyclerView timePeriods;
    private int code;

    ArrayList<Available> oldTimePeriods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listing_setting_new);

        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();

        adapter = new TimePeriodsAdapter(this, getFragmentManager());

        if (bundle != null) {

            code = bundle.getInt("reqCode");
            oldTimePeriods = bundle.getParcelableArrayList(Constant.ARG_POTSPOT_AVAILABLE);

            if (oldTimePeriods != null) {

                adapter.addItems(oldTimePeriods);

                Log.d(TAG, "onCreate: old time periods = " + oldTimePeriods);
            } else {

                //if we not in edit mode we add empty time model
                adapter.addItem(new Available());
            }
        }

        timePeriods.setAdapter(adapter);
        timePeriods.setLayoutManager(new LinearLayoutManager(this));

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


    public void onClickSaveCreateSpot(final View view) {

        if (code == Constant.EDIT_TIME_CODE) {

            Intent intent = new Intent();
            intent.putParcelableArrayListExtra("timePeriods", new ArrayList<>(generateAvailableArr()));
            setResult(RESULT_OK, intent);
            finish();
        } else {

            view.setVisibility(View.INVISIBLE);
            final Intent intent = !SharedPrefsUtils.getBooleanPreference(this, Constant.ADD_SPOT_ONE_TIME, false) ?
                    new Intent(this, AddNewSpotTips.class) : new Intent(this, MySpotsActivity.class);
            Bundle extra = getIntent().getExtras();
            CreateSpotQuery query = new CreateSpotQuery();
            query.token = Person.getToken();
            query.name = extra.getString("title");
            if (!extra.getString("description").isEmpty()) {
                query.about = extra.getString("description");
            }
            query.address = extra.getString("address");
            query.price = Integer.parseInt(extra.getString("price")) * 100;

            if (!extra.getString("maxGuests").isEmpty()) {
                query.maxGuests = Integer.parseInt(extra.getString("maxGuests"));
            }

            if (!extra.getString("listingType").isEmpty()) {
                query.type = extra.getString("listingType");
            }

            if (!extra.getString("tobacco").isEmpty()) {
                query.badges.add(extra.getString("tobacco"));
            }

            if (!extra.getString("heated").isEmpty()) {
                query.badges.add(extra.getString("heated"));
            }

            if (!extra.getString("handicap").isEmpty()) {
                query.badges.add(extra.getString("handicap"));
            }

            query.available = generateAvailableArr();

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            Log.d(TAG, gson.toJson(query));

            Call<SpotCreateResponse> call = mService.createSpot(query);
            call.enqueue(new Callback<SpotCreateResponse>() {
                @Override
                public void onResponse(final Response<SpotCreateResponse> response, Retrofit retrofit) {
                    Log.i(Constant.LOG, new Gson().toJson(response.body()));
                    if (response.body().success) {
                        Log.i(Constant.LOG, String.valueOf(response.code()));
                        Log.i(Constant.LOG, "listing setting success true");
//                    intent.putExtra("id", response);
                        startActivity(intent);
                    } else {
                        Log.i(Constant.LOG, String.valueOf(response.code()));
                        Snackbar.make(findViewById(android.R.id.content), "You already have the spot", Snackbar.LENGTH_INDEFINITE)
                                .setAction("go to spot", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
//                                    intent.putExtra("id", response);
                                        startActivity(intent);
                                    }
                                })
                                .setActionTextColor(getResources().getColor(R.color.mainRed))
                                .show();
                    }
                }

                @Override
                public void onFailure(Throwable t) {
                    Snackbar.make(findViewById(android.R.id.content), Constant.CONNECTION_ERROR, Snackbar.LENGTH_SHORT).show();
                    view.setVisibility(View.VISIBLE);
                }
            });
        }
    }

    private List<Available> generateAvailableArr() {

        List<Available> available = adapter.getItems();

        for (int i = 0; i < available.size(); i++) {
            Available a = available.get(i);

            a.days = getDays((TimePeriodsAdapter.TimePeriod) timePeriods.findViewHolderForAdapterPosition(i));
        }

        return available;
    }

    private List<String> getDays(TimePeriodsAdapter.TimePeriod holder) {

        final List<String> days = new ArrayList<>();

        if (holder.mSunday.isChecked()) {
            days.add("Sunday");
        }

        if (holder.mMonday.isChecked()) {
            days.add("Monday");
        }

        if (holder.mTuesday.isChecked()) {
            days.add("Tuesday");
        }

        if (holder.mWednesday.isChecked()) {
            days.add("Wednesday");
        }

        if (holder.mThursday.isChecked()) {
            days.add("Thursday");
        }

        if (holder.mFriday.isChecked()) {
            days.add("Friday");
        }

        if (holder.mSaturday.isChecked()) {
            days.add("Saturday");
        }

        return days;
    }
}

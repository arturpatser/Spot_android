package com.gridyn.potspot.activity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.gridyn.potspot.Person;
import com.gridyn.potspot.R;
import com.gridyn.potspot.Spot;
import com.gridyn.potspot.adapter.PaidSpotsAdapter;
import com.gridyn.potspot.response.PayedSpotsResponse;
import com.gridyn.potspot.utils.ServerApiUtil;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class PaidSpotsActivity extends AppCompatActivity {

    private static final String TAG = PaidSpotsActivity.class.getName();
    private List<Spot> mSpotList;
    PaidSpotsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paid_spots);
        initToolbar();
        initRecyclerView();

        final TextView title = (TextView) findViewById(R.id.ps_title);
        title.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Roboto-Medium.ttf"));

        loadPaidSpots();
    }

    private void loadPaidSpots() {

        Call<PayedSpotsResponse> call = ServerApiUtil.initUser().showPayedSpotsFriend(Person.getTokenMap());

        call.enqueue(new Callback<PayedSpotsResponse>() {
            @Override
            public void onResponse(Response<PayedSpotsResponse> response, Retrofit retrofit) {

                PayedSpotsResponse payedSpotsResponse = response.body();

                Log.d(TAG, "onResponse: paid = " + payedSpotsResponse);

                adapter.addAll(payedSpotsResponse.getMessage());
            }

            @Override
            public void onFailure(Throwable t) {

                Log.e(TAG, "onFailure: error while load paid spots = " + Log.getStackTraceString(t));
            }
        });
    }

    private void initToolbar() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.ps_toolbar);
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.ps_recycler_view);
        adapter = new PaidSpotsAdapter(getApplicationContext(), this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 1, LinearLayoutManager.VERTICAL, false);
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setItemAnimator(itemAnimator);
    }
}

package com.example.gridyn.potspot.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.gridyn.potspot.Constant;
import com.example.gridyn.potspot.R;
import com.example.gridyn.potspot.Spot;
import com.example.gridyn.potspot.adapter.YourSpotAdapter;
import com.example.gridyn.potspot.response.SpotInfoResponse;
import com.example.gridyn.potspot.service.SpotService;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class MySpotsActivity extends AppCompatActivity {

    private List<Spot> mSpotList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_spots);
        initSpotList();
        initToolbar();
        initRecycler();
    }

    private void initSpotList() {
        mSpotList = new ArrayList<>();

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constant.BASE_URL)
                .build();

        SpotService service = retrofit.create(SpotService.class);
        Call<SpotInfoResponse> call = service.getSpot(getIntent().getExtras().getString("id"));
        call.enqueue(new Callback<SpotInfoResponse>() {
            @Override
            public void onResponse(Response<SpotInfoResponse> response, Retrofit retrofit) {
                //TODO
            }

            @Override
            public void onFailure(Throwable t) {
                Snackbar.make(findViewById(android.R.id.content), Constant.CONNECTION_ERROR, Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    private void initRecycler() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.home_recycler_view);
        YourSpotAdapter adapter = new YourSpotAdapter(mSpotList, getApplicationContext());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 1, LinearLayoutManager.VERTICAL, false);
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setItemAnimator(itemAnimator);

    }

    private void initToolbar() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.your_spot_toolbar);
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void onClickViewSpot(View view) {
        final Intent intent = new Intent(this, ListingActivity.class);
        startActivity(intent);
    }
}
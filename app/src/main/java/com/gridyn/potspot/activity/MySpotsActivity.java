package com.gridyn.potspot.activity;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.gridyn.potspot.Constant;
import com.gridyn.potspot.Person;
import com.gridyn.potspot.R;
import com.gridyn.potspot.Spot;
import com.gridyn.potspot.adapter.YourSpotAdapter;
import com.gridyn.potspot.response.MySpotResponse;
import com.gridyn.potspot.service.UserService;

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

        UserService service = retrofit.create(UserService.class);
        Call<MySpotResponse> call = service.getSpots(Person.getId());
        call.enqueue(new Callback<MySpotResponse>() {
            @Override
            public void onResponse(Response<MySpotResponse> response, Retrofit retrofit) {
                List<MySpotResponse.Message.Spot> data = response.body().message.get(0).spots;
//                Log.i(Constant.LOG, data.get(0).id.$id + " " + data.get(0).data.name + " " + data.get(0).data.address);
//                for (MySpotResponse.Message.Spot spot : data) {
                if (response.body().success) {
                    if (data.size() != 0) {
                        mSpotList.add(new Spot(data.get(0).id.$id, data.get(0).data.name, data.get(0).data.address));
                    }
                }
//                }
            }

            @Override
            public void onFailure(Throwable t) {
                Snackbar.make(findViewById(android.R.id.content), Constant.CONNECTION_ERROR, Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    private void initRecycler() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.your_spot_recycler);
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
//        final Intent intent = new Intent(this, ListingActivity.class);
//        startActivity(intent);
    }
}

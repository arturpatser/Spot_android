package com.gridyn.potspot.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.gridyn.potspot.Person;
import com.gridyn.potspot.R;
import com.gridyn.potspot.adapter.SearchResultAdapter;
import com.gridyn.potspot.response.SpotSearchResponse;
import com.gridyn.potspot.utils.ServerApiUtil;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class FavoritesActivity extends AppCompatActivity {

    private static final String TAG = FavoritesActivity.class.getName();
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.sch_res_toolbar);
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.sch_res_recycler);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1, LinearLayoutManager.VERTICAL, false);
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();

        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setItemAnimator(itemAnimator);

        loadFavorites();
    }

    private void loadFavorites() {

        Call<SpotSearchResponse> call = ServerApiUtil.initUser().getFavorites(Person.getTokenMap());

        call.enqueue(new Callback<SpotSearchResponse>() {
            @Override
            public void onResponse(Response<SpotSearchResponse> response, Retrofit retrofit) {

                SpotSearchResponse spotInfoResponse = response.body();

                if (spotInfoResponse == null) {

                } else {

                    List<SpotSearchResponse.Spots> mSpotList = spotInfoResponse.message.get(0).spots;

                    SearchResultAdapter adapter = new SearchResultAdapter(mSpotList, FavoritesActivity.this,
                            getFragmentManager(), recyclerView);

                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Throwable t) {

                Log.e(TAG, "onFailure: error while load favorites = " + Log.getStackTraceString(t));
            }
        });
    }
}

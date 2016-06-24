package com.example.gridyn.potspot.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.gridyn.potspot.R;
import com.example.gridyn.potspot.Spot;
import com.example.gridyn.potspot.adapter.SearchResultAdapter;

import java.util.ArrayList;
import java.util.List;

public class SearchResultActivity extends AppCompatActivity {

    private List<Spot> mSpotList;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        mContext = getApplicationContext();
        initToolbar();
        initSpotList();
        initRecyclerView();
    }

    private void initToolbar() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.sch_res_toolbar);
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initSpotList() {
        mSpotList = new ArrayList<>();
        mSpotList.add(new Spot("images/chairs.jpg", 20, "Very good backyard in the center", "Backyard", "Donwtown Toronto, Toronto ON"));
        mSpotList.add(new Spot("images/mountain.jpg", 35, "Good balcony for your party", "Backyard", "Donwtown Toronto, Toronto ON"));
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.sch_res_recycler);
        SearchResultAdapter adapter = new SearchResultAdapter(mSpotList, mContext, getFragmentManager());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 1, LinearLayoutManager.VERTICAL, false);
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setItemAnimator(itemAnimator);

    }
}

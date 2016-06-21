package com.example.gridyn.potspot.activity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.gridyn.potspot.R;
import com.example.gridyn.potspot.Spot;
import com.example.gridyn.potspot.adapter.PaidSpotsAdapter;

import java.util.List;

public class PaidSpotsActivity extends AppCompatActivity {

    private List<Spot> mSpotList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paid_spots);
        initToolbar();
        initRecyclerView();

        final TextView title = (TextView) findViewById(R.id.ps_title);
        title.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Roboto-Medium.ttf"));
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
        PaidSpotsAdapter adapter = new PaidSpotsAdapter(mSpotList, getApplicationContext(), this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 1, LinearLayoutManager.VERTICAL, false);
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setItemAnimator(itemAnimator);
    }
}

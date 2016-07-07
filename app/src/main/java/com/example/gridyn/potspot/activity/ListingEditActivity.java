package com.example.gridyn.potspot.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.example.gridyn.potspot.FastBlur;
import com.example.gridyn.potspot.R;
import com.example.gridyn.potspot.Spot;
import com.example.gridyn.potspot.adapter.ListingEditAdapter;

import java.util.ArrayList;
import java.util.List;

public class ListingEditActivity extends AppCompatActivity {

    private List<Spot> mSpotList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listing_edit);
        initSpotList();
        initToolbar();
        initRecycler();
        setHeaderBackground();
    }

    private void setHeaderBackground() {
        final LinearLayout backgroundHead = (LinearLayout) findViewById(R.id.list_edit_blur);
        FastBlur.setBackgroundBlur(backgroundHead, getApplicationContext(),
                mSpotList.get(0).getImage(), getResources());
    }

    private void initSpotList() {
        mSpotList = new ArrayList<>();
        mSpotList.add(new Spot("images/balcony.jpg"));
        mSpotList.add(new Spot("images/chairs.jpg"));
        mSpotList.add(new Spot("images/mountain.jpg"));
        mSpotList.add(new Spot("images/balcony.jpg"));
        mSpotList.add(new Spot("images/chairs.jpg"));
        mSpotList.add(new Spot("images/mountain.jpg"));
        mSpotList.add(new Spot("images/balcony.jpg"));
        mSpotList.add(new Spot("images/chairs.jpg"));
        mSpotList.add(new Spot("images/mountain.jpg"));
    }

    private void initToolbar() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.list_edit_toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initRecycler() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list_edit_recycler);
        ListingEditAdapter adapter = new ListingEditAdapter(mSpotList, getApplicationContext(), this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 1, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setItemAnimator(itemAnimator);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.listing_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.list_edit_menu) {
            return true;
        }else  {
            return super.onOptionsItemSelected(item);
        }
    }

    public void onCLickDoneListing(View view) {
        finish();
    }
}

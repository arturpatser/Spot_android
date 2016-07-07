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
import com.example.gridyn.potspot.adapter.MessageAdapter;

import java.util.ArrayList;
import java.util.List;

public class MessageActivity extends AppCompatActivity {

    private List<Spot> mSpotList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        initSpots();
        initToolbar();
        initRecyclerView();
    }

    private void initSpots() {
        mSpotList = new ArrayList<>();
        mSpotList.add(new Spot("Ethan Hunt", null, "Awesome backyard in downtown"));
        mSpotList.add(new Spot("Forrest Gump", null, "Very good balcony for your party"));
        mSpotList.add(new Spot("Jules Winnfield", null, "Simple patio for smokes"));

        //TODO: retrofit
    }

    private void initToolbar() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.message_toolbar);
        final TextView messageTitle = (TextView) findViewById(R.id.message_title);
        messageTitle.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Roboto-Regular.ttf"));
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.message_recycler);
        MessageAdapter adapter = new MessageAdapter(mSpotList, getApplicationContext(), this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 1, LinearLayoutManager.VERTICAL, false);
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setItemAnimator(itemAnimator);
    }
}

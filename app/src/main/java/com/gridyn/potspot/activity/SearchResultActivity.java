package com.gridyn.potspot.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.gridyn.potspot.R;
import com.gridyn.potspot.SearchBridge;
import com.gridyn.potspot.adapter.SearchResultAdapter;
import com.gridyn.potspot.response.SpotSearchResponse;

import java.util.List;

public class SearchResultActivity extends AppCompatActivity {

    private List<SpotSearchResponse.Spots> mSpotList;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        mContext = getApplicationContext();
        mSpotList = SearchBridge.getDataForSearchResult();
        initToolbar();
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

    private void initRecyclerView() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.sch_res_recycler);
        SearchResultAdapter adapter = new SearchResultAdapter(mSpotList, this, getFragmentManager(), recyclerView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 1, LinearLayoutManager.VERTICAL, false);
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setItemAnimator(itemAnimator);

    }
}

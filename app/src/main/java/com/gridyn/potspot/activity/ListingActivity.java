package com.gridyn.potspot.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ListViewCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.gridyn.potspot.AssetsHelper;
import com.gridyn.potspot.R;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ListingActivity extends AppCompatActivity {

    private List<String> mCommentList;

    private TextView mDescription;
    private RatingBar mRating;
    private TextView mGuests;
    private TextView mTypeSpot;
    private CircleImageView mProfileImage;
    private TextView mProfileTypeSpot;
    private TextView mNameProfile;
    private TextView mProfileDescription;
    private ListViewCompat mComments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listing);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        initFields();
        initToolbar();
        initCommentList();
        initHeader();
        initComments();
//        initRecycler();
    }

    private void initFields() {
        mDescription = (TextView) findViewById(R.id.listing_description);
        mRating = (RatingBar) findViewById(R.id.listing_rating);
        mGuests = (TextView) findViewById(R.id.listing_count_guests);
        mTypeSpot = (TextView) findViewById(R.id.listing_type_spot);
        mProfileImage = (CircleImageView) findViewById(R.id.listing_profile_image);
        mProfileTypeSpot = (TextView) findViewById(R.id.listing_profile_type_spot);
        mNameProfile = (TextView) findViewById(R.id.listing_profile_name);
        mProfileDescription = (TextView) findViewById(R.id.listing_profile_description);
        mComments = (ListViewCompat) findViewById(R.id.listing_comments);

    }

    private void initCommentList() {
        mCommentList = new ArrayList<>();
        mCommentList.add("Very good! I like it! Thank you very match " +
                "\nReally, this backyard the best of my life...");
    }

    private void initHeader() {
        final LinearLayout header = (LinearLayout) findViewById(R.id.listing_header);
        header.setBackground(AssetsHelper.loadImageFromAsset(getApplicationContext(),
                "images/chairs.jpg"));
    }

    private void initToolbar() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.listing_toolbar);
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

/*    private void initRecycler() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list_recycler);
        MyListAdapter adapter = new MyListAdapter(mSpotList, getApplicationContext(), this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 1, LinearLayoutManager.VERTICAL, false);
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setItemAnimator(itemAnimator);
    }*/

    private void initComments() {
        final ListViewCompat comments = (ListViewCompat) findViewById(R.id.listing_comments);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mCommentList);
        comments.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.listing_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.listing_setting) {
            final Intent intent = new Intent(this, ListingSettingActivity.class);
            startActivity(intent);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    public void onClickEditList(View view) {

        //TODO: retrofit

        final Intent intent = new Intent(this, ListingEditActivity.class);
        startActivity(intent);
    }
}

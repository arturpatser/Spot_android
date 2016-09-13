package com.gridyn.potspot.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ListViewCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.gridyn.potspot.Constant;
import com.gridyn.potspot.Person;
import com.gridyn.potspot.R;
import com.gridyn.potspot.model.Available;
import com.gridyn.potspot.response.SpotCommentsResponse;
import com.gridyn.potspot.response.SpotInfoResponse;
import com.gridyn.potspot.utils.ServerApiUtil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class ListingActivity extends AppCompatActivity {

    private static final int EDIT_TIME_CODE = 1;
    private static final String TAG = ListingActivity.class.getName();
    private List<String> mCommentList;

    private TextView mDescription;
    private RatingBar mRatingUp;
    private RatingBar mRatingDown;
    private TextView mGuests;
    private TextView mTypeSpot;
    private CircleImageView mProfileImage;
    private TextView mProfileTypeSpot;
    private TextView mNameProfile;
    private TextView mProfileDescription;
    private ListViewCompat mComments;
    private String mId;
    private ImageView mTypeImg;
    private ImageView mHeader;
    SpotInfoResponse.Message.Spot spot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listing);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        initFields();
        initToolbar();
        initCommentList();
        initComments();
//        initRecycler();

//        loadSpotInfo();
    }

    private void loadSpotInfo() {

        Call<SpotInfoResponse> call = ServerApiUtil.initSpot().getSpot(mId, Person.getTokenMap());
        call.enqueue(new Callback<SpotInfoResponse>() {
            @Override
            public void onResponse(Response<SpotInfoResponse> response, Retrofit retrofit) {

             spot = response.body().message.get(0).spots.get(1);

                if (response.body().success) {
                    Log.i(Constant.LOG, "Id of spot: " + mId);
                    mDescription.setText(spot.name);
                    mTypeSpot.setText(spot.type);
                    mProfileTypeSpot.setText(spot.type);
                    mGuests.setText(String.valueOf(spot.maxGuests));
                    mNameProfile.setText(" " + spot.username);
                    mProfileDescription.setText(spot.about);
                    switch (spot.type) {
                        case "backyard":
                            mTypeImg.setImageDrawable(getResources().getDrawable(R.drawable.backyard));
                            break;
                        case "patio":
                            mTypeImg.setImageDrawable(getResources().getDrawable(R.drawable.patio));
                            break;
                        case "smokingRooms":
                            mTypeImg.setImageDrawable(getResources().getDrawable(R.drawable.other_type_of_spot));
                            break;
                        case "balcony":
                            mTypeImg.setImageDrawable(getResources().getDrawable(R.drawable.balcony));
                            break;
                        case "Backyard":
                            mTypeImg.setImageDrawable(getResources().getDrawable(R.drawable.backyard));
                            break;
                        case "Patio":
                            mTypeImg.setImageDrawable(getResources().getDrawable(R.drawable.patio));
                            break;
                        case "SmokingRooms":
                            mTypeImg.setImageDrawable(getResources().getDrawable(R.drawable.other_type_of_spot));
                            break;
                        case "Balcony":
                            mTypeImg.setImageDrawable(getResources().getDrawable(R.drawable.balcony));
                            break;
                    }
                    if (spot.imgs.size() != 0) {
                        Picasso.with(getApplicationContext())
                                .load(Constant.URL_IMAGE + spot.imgs.get(0))
                                .into(mHeader);
                    } else {
                        Picasso.with(getApplicationContext())
                                .load(Constant.BASE_IMAGE)
                                .into(mHeader);
                    }
                    if (spot.userImgs.size() != 0) {
                        Picasso.with(getApplicationContext())
                                .load(Constant.URL_IMAGE + spot.userImgs.get(0))
                                .into(mProfileImage);
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Snackbar.make(findViewById(android.R.id.content), Constant.CONNECTION_ERROR, Snackbar.LENGTH_LONG)
                        .show();
            }
        });
    }

    private void initFields() {
        mDescription = (TextView) findViewById(R.id.listing_description);
        mRatingUp = (RatingBar) findViewById(R.id.listing_rating_up);
        mRatingDown = (RatingBar) findViewById(R.id.listing_rating_down);
        mGuests = (TextView) findViewById(R.id.listing_count_guests);
        mTypeSpot = (TextView) findViewById(R.id.listing_type_spot);
        mProfileImage = (CircleImageView) findViewById(R.id.listing_profile_image);
        mProfileTypeSpot = (TextView) findViewById(R.id.listing_profile_type_spot);
        mNameProfile = (TextView) findViewById(R.id.listing_profile_name);
        mProfileDescription = (TextView) findViewById(R.id.listing_profile_description);
        mComments = (ListViewCompat) findViewById(R.id.listing_comments);
        mTypeImg = (ImageView) findViewById(R.id.listing_type_img);
        mHeader = (ImageView) findViewById(R.id.listing_header);
        mId = getIntent().getExtras().getString("id");

    }

    private void initCommentList() {
        mCommentList = new ArrayList<>();

        Call<SpotCommentsResponse> call = ServerApiUtil.initSpot().getComments(mId, Person.getTokenMap());
        call.enqueue(new Callback<SpotCommentsResponse>() {
            @Override
            public void onResponse(Response<SpotCommentsResponse> response, Retrofit retrofit) {
//                mCommentList.add("Very good! I like it! Thank you very match " + "\nReally, this backyard the best of my life...");
                if (response.body().message.get(0).comments.size() != 0) {
                    for (SpotCommentsResponse.Comment comment : response.body().message.get(0).comments) {
                        mCommentList.add(comment.data.comment);
                    }
                    initComments();
                } else {
                    final LinearLayout commentsPart = (LinearLayout) findViewById(R.id.listing_comments_part);
                    commentsPart.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Snackbar.make(findViewById(android.R.id.content), Constant.CONNECTION_ERROR, Snackbar.LENGTH_LONG)
                        .show();
            }
        });

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
            if (spot != null) {
                final Intent intent = new Intent(this, ListingSettingActivityNew.class);
                intent.putExtra("reqCode", Constant.EDIT_TIME_CODE);
                intent.putExtra(Constant.ARG_POTSPOT_AVAILABLE, new ArrayList<>(spot.availables));
                startActivityForResult(intent, Constant.EDIT_TIME_CODE);
            }
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    public void onClickEditList(View view) {
//        final Intent intent = new Intent(this, ListingEditActivity.class);
//        intent.putExtra("id", mId);
//        startActivity(intent);

        final Intent intent = new Intent(this, ListingEditActivity.class);
        intent.putExtra("id", mId);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            switch (requestCode) {

                case EDIT_TIME_CODE:

                    ArrayList<Available> availables = data.getParcelableArrayListExtra("timePeriods");

                    Log.d(TAG, "onActivityResult: availables = " + availables );
                    break;
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadSpotInfo();
    }
}

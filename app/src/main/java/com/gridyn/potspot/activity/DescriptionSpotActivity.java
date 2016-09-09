package com.gridyn.potspot.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.databinding.DataBindingUtil;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.gridyn.potspot.Constant;
import com.gridyn.potspot.Person;
import com.gridyn.potspot.R;
import com.gridyn.potspot.databinding.ActivityDescriptionSpotBinding;
import com.gridyn.potspot.fragment.OneTimeFragment;
import com.gridyn.potspot.response.SpotCommentsResponse;
import com.gridyn.potspot.response.SpotInfoResponse;
import com.gridyn.potspot.response.SuccessResponse;
import com.gridyn.potspot.utils.FragmentUtils;
import com.gridyn.potspot.utils.ServerApiUtil;
import com.gridyn.potspot.utils.SharedPrefsUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

import static com.gridyn.potspot.Constant.BASE_IMAGE;
import static com.gridyn.potspot.Constant.CONNECTION_ERROR;
import static com.gridyn.potspot.Constant.FONT_ROBOTO_LIGHT;
import static com.gridyn.potspot.Constant.FONT_ROBOTO_MEDIUM;
import static com.gridyn.potspot.Constant.LOG;
import static com.gridyn.potspot.Constant.URL_IMAGE;


public class DescriptionSpotActivity extends AppCompatActivity {

    private static final String TAG = DescriptionSpotActivity.class.getName();
    private Context mContext;
    private ImageView mHeader;
    private ImageView mTypeImg;
    private CircleImageView mAvatar;
    private TextView mPrice;
    private TextView mName;
    private TextView mGuest;
    private TextView mType;
    private TextView mAbout;
    private TextView mTypeBy;
    private TextView mCountGuests;
    private TextView mUserName;
    private TextView mReviews;
    private ListView mCommentView;
    private Button mBook, minus, plus;
    private List<SpotCommentsResponse.Comment> mAllComment;
    private List<String> mShowComment;
    private ArrayAdapter<String> mCommentsAdapter;
    ImageView favorite;
    SpotInfoResponse.Message.Spot spot;
    ActivityDescriptionSpotBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_description_spot);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        if (!SharedPrefsUtils.getBooleanPreference(this, Constant.SPOT_ONE_TIME, false)) {

            OneTimeFragment oneTimeFragment = OneTimeFragment.newInstance(Constant.SPOT_ONE_TIME, R.drawable.spot_page_1);

            FragmentUtils.openFragment(oneTimeFragment, R.id.content_frame, Constant.SPOT_ONE_TIME, this, true);
        }

        initFields();
        initFonts();
        initRetrofit();
        initToolbar();
        changeCountGuests();
    }

    private void initFields() {
        mContext = getApplicationContext();
        mShowComment = new ArrayList<>();
        mHeader = (ImageView) findViewById(R.id.desc_spot_header);
        mTypeImg = (ImageView) findViewById(R.id.desc_spot_type_img);
        mAvatar = (CircleImageView) findViewById(R.id.desc_spot_avatar);
        mPrice = (TextView) findViewById(R.id.desc_spot_price);
        mName = (TextView) findViewById(R.id.desc_spot_name);
        mGuest = (TextView) findViewById(R.id.desc_spot_guests);
        mType = (TextView) findViewById(R.id.desc_spot_type);
        mTypeBy = (TextView) findViewById(R.id.desc_spot_type_by);
        mAbout = (TextView) findViewById(R.id.desc_spot_about);
        mCountGuests = (TextView) findViewById(R.id.desc_spot_count_guests);
        mUserName = (TextView) findViewById(R.id.desc_spot_name_user);
        mReviews = (TextView) findViewById(R.id.desc_spot_reviews);
        mBook = (Button) findViewById(R.id.desc_spot_book);
        mCommentView = (ListView) findViewById(R.id.desc_spot_comment);
        favorite = (ImageView) findViewById(R.id.favorite);

        minus = (Button) findViewById(R.id.desc_spot_minus);
        plus = (Button) findViewById(R.id.desc_spot_plus);

        minus.setEnabled(false);
        plus.setEnabled(false);
    }

    private void initToolbar() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.desc_spot_toolbar);
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initRetrofit() {
        getSpot();
        getComments();
    }

    private void getComments() {
        Call<SpotCommentsResponse> call = ServerApiUtil.initSpot().getComments(getIntent().getExtras().getString("id"), Person.getTokenMap());
        call.enqueue(new Callback<SpotCommentsResponse>() {
            @Override
            public void onResponse(Response<SpotCommentsResponse> response, Retrofit retrofit) {
                if (response.body().success) {
                    mAllComment = new ArrayList<>(response.body().message.get(0).comments);
                    if (mAllComment.size() > 0) {
                        mReviews.setText(mAllComment.size() + " Reviews");
                        mShowComment.add(mAllComment.get(0).data.comment);
                        mAllComment.remove(0);
                        mCommentsAdapter = new ArrayAdapter<>(DescriptionSpotActivity.this,
                                R.layout.item_comment, mShowComment);
                        mCommentView.setAdapter(mCommentsAdapter);
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Snackbar.make(findViewById(android.R.id.content), CONNECTION_ERROR, Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    public void onCLickReadMoreDesc(View view) {
        for (int i = 0; i < 5; i++) {
            if (mAllComment.size() == 0) {
                final TextView readMore = (TextView) findViewById(R.id.desc_spot_read_more);
                readMore.setVisibility(View.GONE);
                break;
            }
            mShowComment.add(mAllComment.get(0).data.comment);
            mAllComment.remove(0);
            mCommentsAdapter.notifyDataSetChanged();
        }
    }

    private void getSpot() {
        Call<SpotInfoResponse> call = ServerApiUtil.initSpot().getSpot(getIntent().getExtras().getString("id"), Person.getTokenMap());
        call.enqueue(new Callback<SpotInfoResponse>() {
            @Override
            public void onResponse(Response<SpotInfoResponse> response, Retrofit retrofit) {
                if (response.body().success) {

                    minus.setEnabled(true);
                    plus.setEnabled(true);

                    Log.i(LOG, new Gson().toJson(response.body()));
                    spot = response.body().message.get(0).spots.get(1);

                    if (spot.imgs.size() != 0) {
                        Picasso.with(mContext)
                                .load(URL_IMAGE + spot.imgs.get(0))
                                .into(mHeader);
                    } else {
                        Picasso.with(mContext)
                                .load(BASE_IMAGE)
                                .into(mHeader);
                    }

                    spot.price = spot.price / 100;

                    binding.setInFavorites(spot.inFavorites);

                    favorite.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if (binding.getInFavorites()) {

                                Call<SuccessResponse> remove = ServerApiUtil.initSpot().deleteFromFavorite(getIntent().getExtras().getString("id"), Person.getTokenMap());
                                remove.enqueue(new Callback<SuccessResponse>() {
                                    @Override
                                    public void onResponse(Response<SuccessResponse> response, Retrofit retrofit) {

                                        binding.setInFavorites(false);
                                    }

                                    @Override
                                    public void onFailure(Throwable t) {

                                        Log.e(TAG, "onFailure: error while remove from fav = " + Log.getStackTraceString(t));
                                    }
                                });
                            } else {

                                Call<SuccessResponse> add = ServerApiUtil.initSpot().addToFavorite(getIntent().getExtras().getString("id"), Person.getTokenMap());

                                add.enqueue(new Callback<SuccessResponse>() {
                                    @Override
                                    public void onResponse(Response<SuccessResponse> response, Retrofit retrofit) {

                                        binding.setInFavorites(true);
                                    }

                                    @Override
                                    public void onFailure(Throwable t) {

                                        Log.e(TAG, "onFailure: error while remove from fav = " + Log.getStackTraceString(t));
                                    }
                                });
                            }
                        }
                    });

                    mPrice.setText("$ " + spot.price);
                    mName.setText(spot.name);
                    mAbout.setText(spot.about);
                    mGuest.setText(spot.maxGuests + "guests");
                    mType.setText(spot.type);
                    mTypeBy.setText(spot.type);
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
                    mUserName.setText(" " + spot.username);
                    mBook.setText("Book for $" + spot.price);
                    if (spot.userImgs.size() != 0) {
                        Picasso.with(mContext)
                                .load(URL_IMAGE + spot.userImgs.get(0))
                                .into(mAvatar);
                    } else {
                        Picasso.with(mContext)
                                .load(BASE_IMAGE)
                                .into(mAvatar);
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Snackbar.make(findViewById(android.R.id.content), CONNECTION_ERROR, Snackbar.LENGTH_SHORT).show();

                Log.e(TAG, "onFailure: error while show spot = " + Log.getStackTraceString(t));
            }
        });
    }

    public void onCLickBookFor(View view) {
        Intent intent = new Intent(this, BuySpotActivity.class);
        intent.putExtra("id", getIntent().getExtras().getString("id"));
        intent.putExtra(Constant.PARTY_SIZE, mCountGuests.getText().toString());
        intent.putExtra(Constant.OPEN_FOR_BOOK, true);
        startActivity(intent);
    }

    private void changeCountGuests() {

        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer i = Integer.parseInt(mCountGuests.getText().toString());
                if (i > 1) {
                    i--;
                }
                mCountGuests.setText(Integer.toString(i));
            }
        });
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer i = Integer.parseInt(mCountGuests.getText().toString());
                if (i >= 1 && i < spot.maxGuests - 1) {
                    i++;
                }
                mCountGuests.setText(Integer.toString(i));
            }
        });
    }

    private void initFonts() {
        final TextView tobacco = (TextView) findViewById(R.id.desc_spot_tv_tobacco);
        final TextView heated = (TextView) findViewById(R.id.desc_spot_tv_heated);
        final TextView handicap = (TextView) findViewById(R.id.desc_spot_tv_handicap);
        AssetManager asset = getAssets();
        mPrice.setTypeface(Typeface.createFromAsset(asset, FONT_ROBOTO_MEDIUM));
        mTypeBy.setTypeface(Typeface.createFromAsset(asset, FONT_ROBOTO_MEDIUM));
        mUserName.setTypeface(Typeface.createFromAsset(asset, FONT_ROBOTO_MEDIUM));
        mCountGuests.setTypeface(Typeface.createFromAsset(asset, FONT_ROBOTO_MEDIUM));
        mBook.setTypeface(Typeface.createFromAsset(asset, FONT_ROBOTO_MEDIUM));
        mName.setTypeface(Typeface.createFromAsset(asset, FONT_ROBOTO_LIGHT));
        mGuest.setTypeface(Typeface.createFromAsset(asset, FONT_ROBOTO_LIGHT));
        mType.setTypeface(Typeface.createFromAsset(asset, FONT_ROBOTO_LIGHT));
        mAbout.setTypeface(Typeface.createFromAsset(asset, FONT_ROBOTO_LIGHT));
        tobacco.setTypeface(Typeface.createFromAsset(asset, FONT_ROBOTO_LIGHT));
        heated.setTypeface(Typeface.createFromAsset(asset, FONT_ROBOTO_LIGHT));
        handicap.setTypeface(Typeface.createFromAsset(asset, FONT_ROBOTO_LIGHT));
    }
}

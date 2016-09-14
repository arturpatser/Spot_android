package com.gridyn.potspot.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.google.gson.GsonBuilder;
import com.gridyn.potspot.Constant;
import com.gridyn.potspot.Person;
import com.gridyn.potspot.R;
import com.gridyn.potspot.Spot;
import com.gridyn.potspot.adapter.ListingEditAdapter;
import com.gridyn.potspot.query.UpdateSpotQuery;
import com.gridyn.potspot.response.SpotDeleteResponse;
import com.gridyn.potspot.response.SpotSearchResponse;
import com.gridyn.potspot.response.SpotUpdateResponse;
import com.gridyn.potspot.utils.ServerApiUtil;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class ListingEditActivity extends AppCompatActivity {

    private List<Spot> mSpotList;
    private EditText mTitle;
    private EditText mDescription;
    private EditText mAddress;
    private EditText mPrice;
    private EditText mGuests;
    private Spinner mListingTypeSpinner;
    private Spinner mTobaccoSpinner;
    private Spinner mHeatedSpinner;
    private Spinner mHandicapSpinner;
/*    private String mListingType;
    private String mTobacco;
    private String mHeated;
    private String mHandicap;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listing_edit);

        initFields();
        loadFields();
        initToolbar();
        setHeaderBackground();
    }

    private void initFields() {
        mTitle = (EditText) findViewById(R.id.list_edit_title);
        mDescription = (EditText) findViewById(R.id.list_edit_desc);
        mAddress = (EditText) findViewById(R.id.list_edit_address);
        mPrice = (EditText) findViewById(R.id.list_edit_price);
        mGuests = (EditText) findViewById(R.id.list_edit_guests);
        mListingTypeSpinner = (Spinner) findViewById(R.id.list_edit_listing_type);
        mTobaccoSpinner = (Spinner) findViewById(R.id.list_edit_tobacco);
        mHeatedSpinner = (Spinner) findViewById(R.id.list_edit_heated);
        mHandicapSpinner = (Spinner) findViewById(R.id.list_edit_handicap);
    }

    private void loadFields() {
        Call<SpotSearchResponse> call = ServerApiUtil.initSpot().getSpotS(getIntent().getExtras().getString("id"), Person.getTokenMap());
        call.enqueue(new Callback<SpotSearchResponse>() {
            @Override
            public void onResponse(Response<SpotSearchResponse> response, Retrofit retrofit) {
                SpotSearchResponse.Spots spot = response.body().message.get(0).spots.get(0);
                mTitle.setText(spot.data.name);
                mDescription.setText(spot.data.about);
                mAddress.setText(spot.data.address);
                mPrice.setText(String.valueOf(spot.data.price / 100));
                mGuests.setText(String.valueOf(spot.data.maxGuests));

                mTobaccoSpinner.setSelection(1);
                mHeatedSpinner.setSelection(1);
                mHandicapSpinner.setSelection(1);

                initRecycler(spot.data.imgs);

                for (String badge : spot.data.badge) {
                    if (badge.equals(getResources().getString(R.string.tobacco_friendly))) {
                        mTobaccoSpinner.setSelection(0);
                    }
                    if (badge.equals(getResources().getString(R.string.heated))) {
                        mHeatedSpinner.setSelection(0);
                    }
                    if (badge.equals(getResources().getString(R.string.handicap_accessible))) {
                        mHandicapSpinner.setSelection(0);
                    }
                }

                switch (spot.data.type) {
                    case "patio":
                        mListingTypeSpinner.setSelection(0);
                        break;
                    case "backyard":
                        mListingTypeSpinner.setSelection(1);
                        break;
                    case "balcony":
                        mListingTypeSpinner.setSelection(2);
                        break;
                    case "smokingRooms":
                        mListingTypeSpinner.setSelection(3);
                        break;
                    case "Patio":
                        mListingTypeSpinner.setSelection(0);
                        break;
                    case "Backyard":
                        mListingTypeSpinner.setSelection(1);
                        break;
                    case "Balcony":
                        mListingTypeSpinner.setSelection(2);
                        break;
                    case "SmokingRooms":
                        mListingTypeSpinner.setSelection(3);
                        break;
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Snackbar.make(findViewById(android.R.id.content), Constant.CONNECTION_ERROR, Snackbar.LENGTH_LONG)
                        .show();
            }
        });
    }

    private void setHeaderBackground() {
        final LinearLayout backgroundHead = (LinearLayout) findViewById(R.id.list_edit_blur);
//        FastBlur.setBackgroundBlur(backgroundHead, getApplicationContext(),
//                mSpotList.get(0).getImage(), getResources());
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

    private void initRecycler(List<String> imgs) {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list_edit_recycler);
        ListingEditAdapter adapter = new ListingEditAdapter(imgs, getApplicationContext()); //TODO: null -> List<String>
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
        if (item.getItemId() == R.id.list_edit_menu) {
            Call<SpotDeleteResponse> call = ServerApiUtil.initSpot().deleteSpot(Person.getTokenMap());
            call.enqueue(new Callback<SpotDeleteResponse>() {
                @Override
                public void onResponse(Response<SpotDeleteResponse> response, Retrofit retrofit) {
                    Intent intent = new Intent(ListingEditActivity.this, TabsActivity.class);

                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    intent.putExtra("name", Person.getName());
                    intent.putExtra("email", Person.getEmail());

                    try {
                        intent.putExtra("avatar", Person.getAvatar());

                    } catch (IndexOutOfBoundsException e) {
                        intent.putExtra("avatar", Constant.BASE_IMAGE);
                    }

                    startActivity(intent);

                }

                @Override
                public void onFailure(Throwable t) {
                    Snackbar.make(findViewById(android.R.id.content), Constant.CONNECTION_ERROR, Snackbar.LENGTH_LONG)
                            .show();
                }
            });
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    public void onCLickDoneListing(View view) {
        final UpdateSpotQuery query = new UpdateSpotQuery();
        query.id = getIntent().getExtras().getString("id");
        query.token = Person.getToken();
        query.name = mTitle.getText().toString().trim();
        query.about = mDescription.getText().toString().trim();
        query.address = mAddress.getText().toString().trim();
        query.price = Integer.valueOf(mPrice.getText().toString().trim()) * 100;
        query.maxGuests = Integer.valueOf(mGuests.getText().toString().trim());

        mListingTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] choose = getResources().getStringArray(R.array.listing_type);
                query.type = choose[position];
            }

            public void onNothingSelected(AdapterView<?> parent) {/*NOP*/}
        });

        mTobaccoSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    query.badges.add(getResources().getString(R.string.tobacco_friendly));
                }
            }

            public void onNothingSelected(AdapterView<?> parent) {/*NOP*/}
        });
        mHeatedSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    query.badges.add(getResources().getString(R.string.heated));
                }
            }

            public void onNothingSelected(AdapterView<?> parent) {/*NOP*/}
        });
        mHandicapSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    query.badges.add(getResources().getString(R.string.handicap_accessible));
                }
            }

            public void onNothingSelected(AdapterView<?> parent) {/*NOP*/}
        });

        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Log.i(Constant.LOG, "JSON EDIT PROFILE: \n " + builder.create().toJson(query));

        Call<SpotUpdateResponse> call = ServerApiUtil.initSpot().updateSpot(query);
        call.enqueue(new Callback<SpotUpdateResponse>() {
            @Override
            public void onResponse(Response<SpotUpdateResponse> response, Retrofit retrofit) {
                Log.i(Constant.LOG, "ListingEditActivity: " + response.body().success);
                finish();
            }

            @Override
            public void onFailure(Throwable t) {
                Snackbar.make(findViewById(android.R.id.content), Constant.CONNECTION_ERROR, Snackbar.LENGTH_LONG)
                        .show();
            }
        });
    }
}

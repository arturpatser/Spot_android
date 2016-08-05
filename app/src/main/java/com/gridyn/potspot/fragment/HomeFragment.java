package com.gridyn.potspot.fragment;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.gridyn.potspot.Constant;
import com.gridyn.potspot.Person;
import com.gridyn.potspot.R;
import com.gridyn.potspot.SelectPageUtil;
import com.gridyn.potspot.Spot;
import com.gridyn.potspot.activity.SearchCriteriaActivity;
import com.gridyn.potspot.activity.SearchResultActivity;
import com.gridyn.potspot.activity.SpaceActivity;
import com.gridyn.potspot.activity.VerificationActivity;
import com.gridyn.potspot.adapter.HomeAdapter;
import com.gridyn.potspot.query.SearchCriteriaQuery;
import com.gridyn.potspot.response.SpotSearchResponse;
import com.gridyn.potspot.service.SpotService;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class HomeFragment extends Fragment implements OnMapReadyCallback {

    private View mView;
    private List<Spot> mSpotList;
    private boolean flag;
    private Button mFindFirstAvailable;
    private SpotService mService;


    public static HomeFragment getInstance() {
        Bundle args = new Bundle();
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (!flag) {
            flag = true;
            mView = inflater.inflate(R.layout.fragment_home, container, false);

            initRetrofit();
            initSpot();
            initRecyclerView();
            onClickFab();
            onClickFindFirstAvailable();
            setFonts();

            final SupportMapFragment mapFragment = (SupportMapFragment)
                    getChildFragmentManager().findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
        }
        return mView;
    }

    private void initRetrofit() {
        final Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constant.BASE_URL)
                .build();

        mService = retrofit.create(SpotService.class);
    }

    private void initSpot() {
        mSpotList = new ArrayList<>();

        SearchCriteriaQuery query = new SearchCriteriaQuery();
        SearchCriteriaQuery.Distance distance = new SearchCriteriaQuery.Distance();
        distance.lat = 55.755786;
        distance.lng = 37.617633;
        distance.radius = 100000;
        query.distance = distance;
        query.maxGuest = new int[]{1, 1};
        query.badges = new ArrayList<>();
        query.badges.add("tobaccoFriendly");
        query.badges.add("heated");
        query.badges.add("handicap");
        query.price = new int[]{0, 100};
        query.type = new ArrayList<>();
        query.type.add("patio");
        query.type.add("backyard");
        query.type.add("smokingRooms");
        query.type.add("balcony");

        Call<SpotSearchResponse> call = mService.searchSpot(query);
        call.enqueue(new Callback<SpotSearchResponse>() {
            @Override
            public void onResponse(Response<SpotSearchResponse> response, Retrofit retrofit) {
                if (response.body().success) {
                    for (SpotSearchResponse.Spots spot : response.body().message.get(0).spots) {
                        mSpotList.add(new Spot(spot.data.name, spot.data.price,
                                spot.data.type, spot.data.imgs.get(0),
                                spot.data.googlemapsapi.geometry.location.lat, spot.data.googlemapsapi.geometry.location.lat));
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Snackbar.make(getView(), Constant.CONNECTION_ERROR, Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    private void onClickFab() {
        final FloatingActionButton host = (FloatingActionButton) mView.findViewById(R.id.fab_host);
        final FloatingActionButton search = (FloatingActionButton) mView.findViewById(R.id.fab_search);
        host.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Person.isHost()) {
                    Snackbar.make(getView(), "Your account is not verified", Snackbar.LENGTH_INDEFINITE)
                            .setAction("goto verify", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    final Intent intent = new Intent(mView.getContext(), VerificationActivity.class);
                                    startActivity(intent);
                                }
                            })
                            .setActionTextColor(getResources().getColor(R.color.mainRed))
                            .show();
                } else if (Person.isHost()) {
                    final Intent intent = new Intent(mView.getContext(), SpaceActivity.class);
                    startActivity(intent);
                }
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(mView.getContext(), SearchCriteriaActivity.class);
                startActivity(intent);
            }
        });
    }

    private void onClickFindFirstAvailable() {
        mFindFirstAvailable = (Button) mView.findViewById(R.id.btn_find_available);
        mFindFirstAvailable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: retrofit
                Intent intent = new Intent(mView.getContext(), SearchResultActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setFonts() {
        final AssetManager assetManager = getContext().getAssets();
        final TextView listingOnMap = (TextView) mView.findViewById(R.id.home_listing_map);
        final TextView allListing = (TextView) mView.findViewById(R.id.home_all_listing);
        listingOnMap.setTypeface(Typeface.createFromAsset(assetManager, "fonts/Roboto-Regular.ttf"));
        allListing.setTypeface(Typeface.createFromAsset(assetManager, "fonts/Roboto-Regular.ttf"));
        mFindFirstAvailable.setTypeface(Typeface.createFromAsset(assetManager, "fonts/Roboto-Medium.ttf"));
    }

    @Override
    public void onMapReady(GoogleMap map) {
        LatLng toronto = new LatLng(43.453505, 80.465284);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(toronto, 11));
        setMarkers(map);
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = (RecyclerView) mView.findViewById(R.id.home_recycler_view);
        HomeAdapter adapter = new HomeAdapter(mSpotList, getContext(), getActivity());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1, LinearLayoutManager.VERTICAL, false);
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setItemAnimator(itemAnimator);
    }

    public void setMarkers(GoogleMap map) {
        for (Spot spot : mSpotList) {
            map.addMarker(new MarkerOptions()
//                    .icon(BitmapDescriptorFactory.fromBitmap(getMarkerBitmap(spot)))

                    .position(new LatLng(43.453505, 80.465284)));
        }
    }

    private Bitmap getMarkerBitmap(Spot spot) {
        Bitmap.Config conf = Bitmap.Config.ARGB_8888;
        Bitmap bmp = Bitmap.createBitmap(80, 80, conf);
        Canvas canvas1 = new Canvas(bmp);

// paint defines the text color, stroke width and size
        Paint color = new Paint();
        color.setTextSize(35);
        color.setColor(Color.WHITE);

// modify canvas
        canvas1.drawBitmap(BitmapFactory.decodeResource(getResources(),
                R.drawable.home_marker), 0, 0, color);
        canvas1.drawText(spot.getPrice().toString(), 30, 40, color);

        return bmp;
    }

    @Override
    public void onResume() {
        super.onResume();
        SelectPageUtil.selectHome();
    }
}

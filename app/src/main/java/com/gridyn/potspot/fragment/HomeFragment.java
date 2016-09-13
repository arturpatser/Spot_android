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
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.GsonBuilder;
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
import com.gridyn.potspot.model.events.AddToFavoriteEvent;
import com.gridyn.potspot.query.SearchCriteriaQuery;
import com.gridyn.potspot.response.SpotSearchResponse;
import com.gridyn.potspot.service.SpotService;
import com.gridyn.potspot.utils.FragmentUtils;
import com.gridyn.potspot.utils.ServerApiUtil;
import com.gridyn.potspot.utils.SharedPrefsUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class HomeFragment extends Fragment /*implements OnMapReadyCallback */ {

    private static final String TAG = HomeFragment.class.getName();
    private View mView;
    private List<Spot> mSpotList;
    private boolean flag;
    private SpotService mService;
    SwipeRefreshLayout homeRefresher;
    HomeAdapter adapter;


    public static HomeFragment getInstance() {
        Bundle args = new Bundle();
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.home_fragment, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.add_spot) {

            Log.d(TAG, "onOptionsItemSelected: add potspot clicked");

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

            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (!SharedPrefsUtils.getBooleanPreference(getContext(), Constant.HOME_ONE_TIME, false)) {

            OneTimeFragment oneTimeFragment = OneTimeFragment.newInstance(Constant.HOME_ONE_TIME, R.drawable.home_1);

            FragmentUtils.openFragment(oneTimeFragment, R.id.drawer_layout, Constant.HOME_ONE_TIME, getContext(), true);
        }

        if (!flag) {
            flag = true;
            mView = inflater.inflate(R.layout.fragment_home, container, false);

            homeRefresher = (SwipeRefreshLayout) mView.findViewById(R.id.home_refresher);
            homeRefresher.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    initSpot();
                }
            });
            initRetrofit();
            initSpot();
            onClickFab();
//            setFonts();

//            final SupportMapFragment mapFragment = (SupportMapFragment)
//                    getChildFragmentManager().findFragmentById(R.id.map);
//            mapFragment.getMapAsync(this);
        }
        return mView;
    }

    private void initRetrofit() {
        final Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constant.BASE_URL)
                .build();

        mService = ServerApiUtil.initSpot();
    }

    private void initSpot() {
        mSpotList = new ArrayList<>();

        SearchCriteriaQuery query = new SearchCriteriaQuery();
        SearchCriteriaQuery.Distance distance = new SearchCriteriaQuery.Distance();
        query.token = Person.getToken();
        distance.lat = 55.755786;
        distance.lng = 37.617633;
        distance.radius = 100000000;
        query.distance = distance;
        query.maxGuest = new int[]{1, 5};
        query.badges = new ArrayList<>();
        query.badges.add("tobaccoFriendly");
        query.badges.add("heated");
        query.badges.add("handicap");
        query.price = new int[]{0, 100000};
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
                    homeRefresher.setRefreshing(false);

                    for (SpotSearchResponse.Spots spot : response.body().message.get(0).spots) {
                        mSpotList.add(new Spot(spot.id.$id, spot.data.name, spot.data.price / 100,
                                spot.data.type, spot.data.imgs.size() > 0 ? spot.data.imgs.get(0) : null,
                                spot.data.googlemapsapi.geometry.location.lat, spot.data.googlemapsapi.geometry.location.lat
                        ,spot.data.inFavorites));
                    }
                    initRecyclerView();
                } else {
                    Log.d(TAG, "onResponse: " + new GsonBuilder().setPrettyPrinting().create().toJson(response.body()));
                }
            }

            @Override
            public void onFailure(Throwable t) {

                Log.d(TAG, "onFailure: error while load spots = " + Log.getStackTraceString(t));
                homeRefresher.setRefreshing(false);
                Snackbar.make(getView(), Constant.CONNECTION_ERROR, Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    private void onClickFab() {
        final FloatingActionButton firstAvailable = (FloatingActionButton) mView.findViewById(R.id.fab_host);
        final FloatingActionButton search = (FloatingActionButton) mView.findViewById(R.id.fab_search);
        firstAvailable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d(TAG, "onClick: find first available clicked");

                Intent intent = new Intent(mView.getContext(), SearchResultActivity.class);
//                startActivity(intent);
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

    private void setFonts() {
        final AssetManager assetManager = getContext().getAssets();
        final TextView listingOnMap = (TextView) mView.findViewById(R.id.home_listing_map);
        final TextView allListing = (TextView) mView.findViewById(R.id.home_all_listing);
        listingOnMap.setTypeface(Typeface.createFromAsset(assetManager, "fonts/Roboto-Regular.ttf"));
        allListing.setTypeface(Typeface.createFromAsset(assetManager, "fonts/Roboto-Regular.ttf"));
    }

//    @Override
//    public void onMapReady(GoogleMap map) {
//        LatLng toronto = new LatLng(43.453505, 80.465284);
//        map.moveCamera(CameraUpdateFactory.newLatLngZoom(toronto, 11));
//        setMarkers(map);
//    }

    private void initRecyclerView() {
        RecyclerView recyclerView = (RecyclerView) mView.findViewById(R.id.home_recycler_view);
        adapter = new HomeAdapter(mSpotList, getContext(), getChildFragmentManager());
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

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onFavoriteSelected(AddToFavoriteEvent addToFavoriteEvent) {

        Log.d(TAG, "onFavoriteSelected: event = " + addToFavoriteEvent);

        adapter.updateSpot(addToFavoriteEvent.getSpotId(), addToFavoriteEvent.isFavorite());

        EventBus.getDefault().removeStickyEvent(addToFavoriteEvent);
    }
}

package com.example.gridyn.potspot.fragment;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
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

import com.example.gridyn.potspot.R;
import com.example.gridyn.potspot.Spot;
import com.example.gridyn.potspot.activity.SpaceActivity;
import com.example.gridyn.potspot.activity.SearchCriteriaActivity;
import com.example.gridyn.potspot.activity.SearchResultActivity;
import com.example.gridyn.potspot.adapter.HomeAdapter;
import com.github.clans.fab.FloatingActionButton;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements OnMapReadyCallback {

    private View mView;
    private List<Spot> mSpotList;
    private boolean flag;
    private Button mFindFirstAvailable;


    public static HomeFragment getInstance() {
        Bundle args = new Bundle();
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(!flag) {
            flag = true;
            mView = inflater.inflate(R.layout.fragment_home, container, false);

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

    private void initSpot() {
        mSpotList = new ArrayList<>();

        //TODO: retrofit

        mSpotList.add(new Spot("Title", 35, "Balcony", "images/balcony.jpg"));
        mSpotList.add(new Spot("Title", 45, "Backyard", "images/chairs.jpg"));
        mSpotList.add(new Spot("Title", 15, "Mountains", "images/mountain.jpg"));
    }

    private void onClickFab() {
        final FloatingActionButton host = (FloatingActionButton) mView.findViewById(R.id.fab_host);
        final FloatingActionButton search = (FloatingActionButton) mView.findViewById(R.id.fab_search);
        host.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(mView.getContext(), SpaceActivity.class);
                startActivity(intent);
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
        LatLng toronto = new LatLng(43.682653, -79.401435);

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(toronto, 13));

        map.addMarker(new MarkerOptions()
                .title("Toronto")
                .snippet("The most populous city of Canada.")
                .position(toronto));
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
}
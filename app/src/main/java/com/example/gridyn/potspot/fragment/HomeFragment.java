package com.example.gridyn.potspot.fragment;

import android.content.res.AssetManager;
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

import com.example.gridyn.potspot.R;
import com.example.gridyn.potspot.Spot;
import com.example.gridyn.potspot.adapter.HomeAdapter;
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
    private Button mFindAvailable;


    public static HomeFragment getInstance() {
        Bundle args = new Bundle();
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(!flag) {
            mView = inflater.inflate(R.layout.fragment_home, container, false);
            flag = true;
        }

        mFindAvailable = (Button) mView.findViewById(R.id.btn_find_available);

        initSpotList();
        initRecyclerView();
        setFonts();

        SupportMapFragment mapFragment = (SupportMapFragment)
                getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mFindAvailable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(mView, "find first available", Snackbar.LENGTH_LONG).show();
            }
        });

      /*  FloatingActionButton fab = (FloatingActionButton) mView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/

        return mView;
    }

    private void setFonts() {
        final AssetManager assetManager = getContext().getAssets();
        final TextView listingOnMap = (TextView) mView.findViewById(R.id.home_listing_map);
        final TextView allListing = (TextView) mView.findViewById(R.id.home_all_listing);
        listingOnMap.setTypeface(Typeface.createFromAsset(assetManager, "fonts/Roboto-Regular.ttf"));
        allListing.setTypeface(Typeface.createFromAsset(assetManager, "fonts/Roboto-Regular.ttf"));
        mFindAvailable.setTypeface(Typeface.createFromAsset(assetManager, "fonts/Roboto-Medium.ttf"));
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

    private void initSpotList() {
        mSpotList = new ArrayList<>();
        mSpotList.add(new Spot("Title", 35, "Balcony", "images/balcony.jpg"));
        mSpotList.add(new Spot("Title", 45, "Backyard", "images/chairs.jpg"));
        mSpotList.add(new Spot("Title", 15, "Mountains", "images/mountain.jpg"));
    }
}

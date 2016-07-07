package com.example.gridyn.potspot.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gridyn.potspot.Person;
import com.example.gridyn.potspot.R;
import com.example.gridyn.potspot.Spot;
import com.example.gridyn.potspot.adapter.NotificationClientAdapter;
import com.example.gridyn.potspot.adapter.NotificationHostAdapter;

import java.util.ArrayList;
import java.util.List;

public class NotificationFragment extends Fragment {

    private View mView;
    private List<Spot> mSpotList;

    public static NotificationFragment getInstance() {
        Bundle args = new Bundle();
        NotificationFragment fragment = new NotificationFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(Person.isHost()) {
            mView = inflater.inflate(R.layout.fragment_notification_host, container, false);
            initRecyclerViewHost();
        } else if(!Person.isHost()) {
            mView = inflater.inflate(R.layout.fragment_notification_client, container, false);
            initRecyclerViewClient();
        }
        return mView;
    }

    private void initSpotHost() {
        mSpotList = new ArrayList<>();

        //TODO: retrofit

        mSpotList.add(new Spot("Andrey", "images/balcony.jpg", "Balcony"));
        mSpotList.add(new Spot("Petr", "images/chairs.jpg", "Backyard"));
        mSpotList.add(new Spot("Leha", "images/mountain.jpg", "Mountains"));
    }

    private void initSpotClient() {
        mSpotList = new ArrayList<>();

        //TODO: retrofit

        mSpotList.add(new Spot("Title", 35, "Balcony", "images/balcony.jpg"));
        mSpotList.add(new Spot("Title", 45, "Backyard", "images/chairs.jpg"));
        mSpotList.add(new Spot("Title", 15, "Mountains", "images/mountain.jpg"));
    }

    private void initRecyclerViewHost() {
        initSpotHost();
        RecyclerView recyclerView = (RecyclerView) mView.findViewById(R.id.notification_host_recycler_view);
        NotificationHostAdapter adapter = new NotificationHostAdapter(mSpotList, getContext(), getActivity());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1, LinearLayoutManager.VERTICAL, false);
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setItemAnimator(itemAnimator);
    }

    private void initRecyclerViewClient() {
        initSpotClient();
        RecyclerView recyclerView = (RecyclerView) mView.findViewById(R.id.notification_client_recycler_view);
        NotificationClientAdapter adapter = new NotificationClientAdapter(mSpotList, getContext(), getActivity());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1, LinearLayoutManager.VERTICAL, false);
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setItemAnimator(itemAnimator);
    }
}
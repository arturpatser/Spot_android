package com.gridyn.potspot.fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gridyn.potspot.Person;
import com.gridyn.potspot.R;
import com.gridyn.potspot.SelectPageUtil;
import com.gridyn.potspot.adapter.NotifsAdapter;
import com.gridyn.potspot.model.NotificationModel;
import com.gridyn.potspot.model.events.ReceivedNotifEvent;
import com.gridyn.potspot.model.notificationsModels.Message;
import com.gridyn.potspot.utils.ServerApiUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class NotificationFragment extends Fragment {

    private static final String TAG = NotificationFragment.class.getName();

    @BindView(R.id.notification_host_recycler_view)
    RecyclerView notifsRecyclerView;

    @BindView(R.id.notifs_refresher)
    SwipeRefreshLayout swipeRefreshLayout;

    NotifsAdapter notifsAdapter;

    public static NotificationFragment getInstance() {
        Bundle args = new Bundle();
        NotificationFragment fragment = new NotificationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EventBus.getDefault().register(this);

        notifsAdapter = new NotifsAdapter(getContext());

        loadNotifications();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        if (Person.isHost()) {
//            mView = inflater.inflate(R.layout.fragment_notification_host, container, false);
//            initRecyclerViewHost();
//        } else if (!Person.isHost()) {
//            mView = inflater.inflate(R.layout.fragment_notification_client, container, false);
//            initRecyclerViewClient();
//        }

        View rootView = inflater.inflate(R.layout.fragment_notification_host, container, false);

        ButterKnife.bind(this, rootView);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        notifsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        notifsRecyclerView.setAdapter(notifsAdapter);

        notifsAdapter.setParent(notifsRecyclerView);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                loadNotifications();
            }
        });
    }

    private void loadNotifications() {

        Call<NotificationModel> call = ServerApiUtil.initUser().getAllNotifs(Person.getTokenMap());

        call.enqueue(new Callback<NotificationModel>() {
            @Override
            public void onResponse(Response<NotificationModel> response, Retrofit retrofit) {

                swipeRefreshLayout.setRefreshing(false);

                NotificationModel notificationModel = response.body();

                if (notificationModel == null) {

                } else {

                    Log.d(TAG, "onResponse: notifs = " + notificationModel);

                    if (notificationModel.getSuccess()) {

                        notifsAdapter.cleanArray();

                        List<Message> notifsArray = notificationModel.getMessage().get(0);

                        notifsAdapter.addAll(notifsArray);
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {

                Log.e(TAG, "onFailure: error while load notifs = " + Log.getStackTraceString(t));

                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onReceivedNotification(ReceivedNotifEvent receivedNotifEvent) {

        Log.d(TAG, "onReceivedNotification: received new notif");

        loadNotifications();

        EventBus.getDefault().removeStickyEvent(receivedNotifEvent);
    }

//    private void initSpotHost() {
//        mSpotList = new ArrayList<>();
//
//        //TODO: retrofit
//
//      /*  mSpotList.add(new Spot("Andrey", "images/balcony.jpg", "Balcony"));
//        mSpotList.add(new Spot("Petr", "images/chairs.jpg", "Backyard"));
//        mSpotList.add(new Spot("Leha", "images/mountain.jpg", "Mountains"));*/
//    }
//
//    private void initSpotClient() {
//        mSpotList = new ArrayList<>();
//
//        //TODO: retrofit
//
//      /*  mSpotList.add(new Spot("Title", 35, "Balcony", "images/balcony.jpg"));
//        mSpotList.add(new Spot("Title", 45, "Backyard", "images/chairs.jpg"));
//        mSpotList.add(new Spot("Title", 15, "Mountains", "images/mountain.jpg"));*/
//    }
//
//    private void initRecyclerViewHost() {
//        initSpotHost();
//        RecyclerView recyclerView = (RecyclerView) mView.findViewById(R.id.notification_host_recycler_view);
//        NotificationHostAdapter adapter = new NotificationHostAdapter(mSpotList, getContext(), getActivity());
//        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1, LinearLayoutManager.VERTICAL, false);
//        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
//
//        recyclerView.setAdapter(adapter);
//        recyclerView.setLayoutManager(gridLayoutManager);
//        recyclerView.setItemAnimator(itemAnimator);
//    }
//
//    private void initRecyclerViewClient() {
//        initSpotClient();
//        RecyclerView recyclerView = (RecyclerView) mView.findViewById(R.id.notification_client_recycler_view);
//        NotificationClientAdapter adapter = new NotificationClientAdapter(mSpotList, getContext(), getActivity());
//        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1, LinearLayoutManager.VERTICAL, false);
//        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
//
//        recyclerView.setAdapter(adapter);
//        recyclerView.setLayoutManager(gridLayoutManager);
//        recyclerView.setItemAnimator(itemAnimator);
//    }


    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        SelectPageUtil.selectNotification();
    }
}
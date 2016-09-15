package com.gridyn.potspot.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.gridyn.potspot.Person;
import com.gridyn.potspot.R;
import com.gridyn.potspot.adapter.PaymentHistoryAdapter;
import com.gridyn.potspot.databinding.ActivityMyMoneyHostBinding;
import com.gridyn.potspot.model.NotificationModel;
import com.gridyn.potspot.model.PaymentHistoryItem;
import com.gridyn.potspot.model.notificationsModels.Message;
import com.gridyn.potspot.utils.ServerApiUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class MyMoneyActivity extends AppCompatActivity {

    private static final String TAG = MyMoneyActivity.class.getName();
    /**
     * Do not forget check binding != null, because we have two content views at this class
     */
    ActivityMyMoneyHostBinding binding;
    RecyclerView paymentHistoryRecycler;
    private PaymentHistoryAdapter paymentHistoryAdapter;
    int numColumns = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Person.isHost()) {

            binding = DataBindingUtil.setContentView(this, R.layout.activity_my_money_host);

            paymentHistoryAdapter = new PaymentHistoryAdapter(this);

            loadMyMoneyInfo();

            paymentHistoryRecycler = (RecyclerView) binding.getRoot().findViewById(R.id.payment_history_recycler);

            GridLayoutManager manager = new GridLayoutManager(this, numColumns);
            manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {

                    //set span for headers
                    if (position == 0 || position == 1)
                        return 2;

                    return 1;
                }
            });

            paymentHistoryRecycler.setLayoutManager(manager);
            paymentHistoryRecycler.setAdapter(paymentHistoryAdapter);

           } else {

            setContentView(R.layout.activity_my_money_no_host);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getSupportActionBar().setDisplayShowTitleEnabled(false);


    }

    private void loadMyMoneyInfo() {

        //TODO change blank values by real, implement retrofit logic here
        binding.setMoneyValue("20");

//        paymentHistoryAdapter.setMyMoneyDetails("01/07", "55");
//
//        final ArrayList<PaymentHistoryItem> paymentHistoryItemArrayList = new ArrayList<>();
//
//        Random r = new Random();
//
//        for (int i = 0; i < 10; i++) {
//
//            int randDay = Math.abs(r.nextInt() % 31);
//            int randMonth = Math.abs(r.nextInt() % 12);
//            int randValue = Math.abs(r.nextInt() % 200);
//            paymentHistoryItemArrayList.add(new PaymentHistoryItem(randDay + "/" + randMonth, "" + randValue));
//        }
//
//        paymentHistoryAdapter.addItems(paymentHistoryItemArrayList);

        Call<NotificationModel> call = ServerApiUtil.initUser().showPayedSpots(Person.getTokenMap());

        call.enqueue(new Callback<NotificationModel>() {
            @Override
            public void onResponse(Response<NotificationModel> response, Retrofit retrofit) {

                NotificationModel paidSpots = response.body();

                Log.d(TAG, "onResponse: paid = " + paidSpots);

                paymentHistoryAdapter.clean();

                List<Message> notifsArray = paidSpots.getMessage().get(0);

                ArrayList<PaymentHistoryItem> paymentHistoryItems = new ArrayList<>();

                SimpleDateFormat format = new SimpleDateFormat("MM-dd");

                for (Message message : notifsArray) {

                    PaymentHistoryItem paymentHistoryItem = new PaymentHistoryItem(format
                            .format(new Date(message.getSystem().getTimeCreated())),
                            String.valueOf(message.getSystem().getFullPrice() / 100) );

                    paymentHistoryItems.add(paymentHistoryItem);

                }

                paymentHistoryAdapter.addAll(paymentHistoryItems);
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });

        Call<NotificationModel> call1 = ServerApiUtil.initUser().showPayedSpotsHost(Person.getTokenMap());

        call1.enqueue(new Callback<NotificationModel>() {
            @Override
            public void onResponse(Response<NotificationModel> response, Retrofit retrofit) {
                NotificationModel paidSpots = response.body();

                Log.d(TAG, "onResponse: paid = " + paidSpots);

                List<Message> notifsArray = paidSpots.getMessage().get(0);

                int totalGet = 0;

                for (Message message : notifsArray) {

                    totalGet += message.getSystem().getFullPrice() / 100;
                }

                binding.setMoneyValue(""+totalGet);
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    //onclick for no host
    public void onBecomeHostClick(View view) {

        if (!Person.isHost()) {
            Snackbar.make(view, "Your account is not verified", Snackbar.LENGTH_INDEFINITE)
                    .setAction("goto verify", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final Intent intent = new Intent(MyMoneyActivity.this, VerificationActivity.class);
                            startActivity(intent);
                        }
                    })
                    .setActionTextColor(getResources().getColor(R.color.mainRed))
                    .show();
        } else if (Person.isHost()) {
            startActivity(new Intent(this, SpaceActivity.class));
        }
    }
}

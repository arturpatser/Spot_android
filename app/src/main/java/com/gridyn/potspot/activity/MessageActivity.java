package com.gridyn.potspot.activity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.gridyn.potspot.Constant;
import com.gridyn.potspot.Person;
import com.gridyn.potspot.R;
import com.gridyn.potspot.adapter.MessageAdapter;
import com.gridyn.potspot.model.Message;
import com.gridyn.potspot.response.MessageLastResponse;
import com.gridyn.potspot.response.UserInfoResponse;
import com.gridyn.potspot.service.ChatService;
import com.gridyn.potspot.service.UserService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class MessageActivity extends AppCompatActivity {

    private List<Message> mMessageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        initSpots();
        initToolbar();
    }

    private void initSpots() {
        final Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constant.BASE_URL)
                .build();

        Map<String, String> map = new HashMap<>();
        map.put("token", Person.getToken());

        Log.i(Constant.LOG, "MessageListQuery: " + new Gson().toJson(map));

        final ChatService service = retrofit.create(ChatService.class);
        Call<MessageLastResponse> call = service.showLastMessage(map);
        call.enqueue(new Callback<MessageLastResponse>() {
            @Override
            public void onResponse(Response<MessageLastResponse> response, Retrofit retrofit) {
                mMessageList = new ArrayList<>();
                Log.i(Constant.LOG, "MessageListResponse: " + new GsonBuilder().setPrettyPrinting().create().toJson(response.body()));
                if (response.body().success && response.body().message.get(0).messages.size() != 0) {
                    List<MessageLastResponse.Message.Message_> messages = response.body().message.get(0).messages.get(0);
                    for(int messageCount = 0; messageCount < messages.size(); messageCount++) {
                        mMessageList.add(new Message(messages.get(messageCount).system.user,
                                messages.get(messageCount).data.message, new SimpleDateFormat("dd.mm")
                                .format(new Date((long) messages.get(messageCount).system.timeCreated * 1000))));
                        loadInfoAboutUser(retrofit, messages.get(messageCount).system.user, messageCount);
                    }
                }
                initRecyclerView();
            }

            @Override
            public void onFailure(Throwable t) {
                Snackbar.make(findViewById(android.R.id.content),
                        Constant.CONNECTION_ERROR, Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private void loadInfoAboutUser(Retrofit retrofit, String user, final Integer messageCount) {
        final UserService service = retrofit.create(UserService.class);
        final Map<String, String> map = new HashMap<>();
        map.put("token", Person.getToken());
        Call<UserInfoResponse> call = service.getUserInfo(user, map);
        call.enqueue(new Callback<UserInfoResponse>() {
            @Override
            public void onResponse(Response<UserInfoResponse> response, Retrofit retrofit) {
                if(response.body().success) {
                    UserInfoResponse.Message.Data user = response.body().message.get(0).data;
                    mMessageList.get(messageCount).setFromName(user.name);
                    mMessageList.get(messageCount).setImgUser(user.imgs.get(0));
                    if (!user.spot.isEmpty()) {
                        mMessageList.get(messageCount).setSpotName(user.spot.get(0).data.name);
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Snackbar.make(findViewById(android.R.id.content),
                        Constant.CONNECTION_ERROR, Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private void initToolbar() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.message_toolbar);
        final TextView messageTitle = (TextView) findViewById(R.id.message_title);
        messageTitle.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Roboto-Regular.ttf"));
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.message_recycler);
        MessageAdapter adapter = new MessageAdapter(mMessageList, getApplicationContext(), this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 1, LinearLayoutManager.VERTICAL, false);
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setItemAnimator(itemAnimator);
    }
}

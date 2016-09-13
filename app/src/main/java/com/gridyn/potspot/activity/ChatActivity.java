package com.gridyn.potspot.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.GsonBuilder;
import com.gridyn.potspot.Constant;
import com.gridyn.potspot.MessageUtil;
import com.gridyn.potspot.Person;
import com.gridyn.potspot.R;
import com.gridyn.potspot.adapter.ChatAdapter;
import com.gridyn.potspot.model.Message;
import com.gridyn.potspot.query.SendMessageQuery;
import com.gridyn.potspot.response.MessageHistoryResponse;
import com.gridyn.potspot.response.MessageSendResponse;
import com.gridyn.potspot.service.ChatService;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

import static com.gridyn.potspot.Constant.*;

public class ChatActivity extends AppCompatActivity {
    private ImageButton mBtnSend;
    private EditText mInputMsg;

    // Chat messages list adapter
    private ChatAdapter adapter;
    private List<Message> listMessages;
    private ListView listViewMessages;

    private MessageUtil utils;

    private String mNameUser;
    private String mNameSpot;
    private String mUserId;

    private ChatService mService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        initRetrofit();
        initFields();
        initHeader();
        loadHistory();
        initToolbar();
        receiveMessage();
    }

    private void initHeader() {
        final TextView nameUser = (TextView) findViewById(R.id.ch_h_name);
        final TextView nameSpot = (TextView) findViewById(R.id.ch_h_about);
        final ImageView background = (ImageView) findViewById(R.id.ch_h_back);
        final ImageView imageUser = (ImageView) findViewById(R.id.ch_h_img);
        nameUser.setText(mNameUser);
        nameSpot.setText(mNameSpot);

        if (getIntent().getExtras().getString("imgSpot") != null) {
            Picasso.with(this)
                    .load(URL_IMAGE + getIntent().getExtras().getString("imgSpot"))
                    .into(background);
        } else {
            Picasso.with(this)
                    .load(URL_IMAGE + BASE_IMAGE_OF_SPOT)
                    .into(background);
        }

        if (getIntent().getExtras().getString("imgUser") != null) {
            Picasso.with(this)
                    .load(URL_IMAGE + getIntent().getExtras().getString("imgUser"))
                    .into(imageUser);
        } else {
            Picasso.with(this)
                    .load(BASE_IMAGE)
                    .into(imageUser);
        }
    }

    private void receiveMessage() {
        final BroadcastReceiver registrationBroadcast = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
/*                if (intent.getAction().equals(GCMRegistrationIntentService.REGISTRATION_SUCCESS)) {
                    String token = intent.getStringExtra("token");
                    Log.i(Constant.LOG, "GCM token:" + token);
                    appendMessage(token, false);
                } else if (intent.getAction().equals(GCMRegistrationIntentService.REGISTRATION_ERROR)) {
                    Log.i(Constant.LOG, "GCM registration error!!!");
                }*/
                String message = intent.getStringExtra("message");
                appendMessage(message, false);
            }
        };

        registerReceiver(registrationBroadcast, new IntentFilter(Constant.MESSAGE_ACTION));
    }

    private void initRetrofit() {
        final Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constant.BASE_URL)
                .build();

        mService = retrofit.create(ChatService.class);
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.chat_recycler);
        adapter = new ChatAdapter(this, listMessages);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 1, LinearLayoutManager.VERTICAL, false);
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setItemAnimator(itemAnimator);
        recyclerView.scrollToPosition(listMessages.size() - 1);
    }

    private void initFields() {
        mBtnSend = (ImageButton) findViewById(R.id.chat_send);
        mInputMsg = (EditText) findViewById(R.id.chat_input);
        utils = new MessageUtil(getApplicationContext());
        listMessages = new ArrayList<Message>();

        Bundle args = getIntent().getExtras();
        mNameUser = args.getString("fromName");
        mNameSpot = args.getString("spotName");
        mUserId = args.getString("userId");
    }

    private void initToolbar() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.chat_toolbar);
        final TextView title = (TextView) findViewById(R.id.chat_title);
        title.setText(mNameUser);
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void loadHistory() {
        Call<MessageHistoryResponse> call = mService.showHistoryMessage(mUserId, Person.getTokenMap());
        call.enqueue(new Callback<MessageHistoryResponse>() {
            @Override
            public void onResponse(Response<MessageHistoryResponse> response, Retrofit retrofit) {
                Log.i("log", "onResponse: " + new GsonBuilder().setPrettyPrinting().create().toJson(response.body()));
                List<MessageHistoryResponse.Message.Message_> messages = response.body().message.get(0).messages;
                for (MessageHistoryResponse.Message.Message_ message : messages) {
                    Log.i("log", "onResponse: " + message.data.message);
                    if (message.system.owner.equals(Person.getId())) {
                        listMessages.add(new Message(Person.getName(), message.data.message, true));
                    } else {
                        listMessages.add(new Message(mNameUser, message.data.message, false));
                    }
                }
                initRecyclerView();
            }

            @Override
            public void onFailure(Throwable t) {
                Snackbar.make(findViewById(android.R.id.content), Constant.CONNECTION_ERROR, Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private void appendMessage(String message, boolean isSelf) {
        Message msg;
        if (isSelf) {
            msg = new Message("Dyuha", mInputMsg.getText().toString().trim(), true);
        } else {
            msg = new Message(mNameUser, message, false);
        }
        listMessages.add(msg);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    public void playBeep() {

        try {
            Uri notification = RingtoneManager
                    .getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(),
                    notification);
            r.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected final static char[] hexArray = "0123456789ABCDEF".toCharArray();

    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    public void onClickSendMessage(final View view) {
        if (!mInputMsg.getText().toString().isEmpty()) {
            final SendMessageQuery query = new SendMessageQuery();
            query.token = Person.getToken();
            query.message = mInputMsg.getText().toString().trim();
            Call<MessageSendResponse> call = mService.sendMessage(mUserId, query);
            call.enqueue(new Callback<MessageSendResponse>() {
                @Override
                public void onResponse(Response<MessageSendResponse> response, Retrofit retrofit) {
                    if (response.body().success) {
                        appendMessage(query.message, true);
                        mInputMsg.setText(null);
                    }
                }

                @Override
                public void onFailure(Throwable t) {
                    Snackbar.make(view, Constant.CONNECTION_ERROR, Snackbar.LENGTH_LONG).show();
                }
            });
        }
    }
}

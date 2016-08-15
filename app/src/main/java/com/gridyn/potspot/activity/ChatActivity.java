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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.gridyn.potspot.Constant;
import com.gridyn.potspot.MessageUtil;
import com.gridyn.potspot.Person;
import com.gridyn.potspot.R;
import com.gridyn.potspot.adapter.ChatAdapter;
import com.gridyn.potspot.model.Message;
import com.gridyn.potspot.query.SendMessageQuery;
import com.gridyn.potspot.response.SendMessageResponse;
import com.gridyn.potspot.service.ChatService;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class ChatActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private Button mBtnSend;
    private EditText mInputMsg;

    // Chat messages list adapter
    private ChatAdapter adapter;
    private List<Message> listMessages;
    private ListView listViewMessages;

    private MessageUtil utils;

    // Client name
    private String name = null;

    private ChatService mService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        initRetrofit();
        initFields();
        initRecyclerView();
        initToolbar();
        initProfile();
        loadHistory();
        receiveMessage();
    }

    private void receiveMessage() {
        final BroadcastReceiver registrationBroadcast = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
/*                if (intent.getAction().equals(GCMRegistrationIntentService.REGISTRATION_SUCCESS)) {
                    String token = intent.getStringExtra("token");
                    Log.i(Constant.LOG, "GCM token:" + token);
                    sendMessageToServer(token, false);
                } else if (intent.getAction().equals(GCMRegistrationIntentService.REGISTRATION_ERROR)) {
                    Log.i(Constant.LOG, "GCM registration error!!!");
                }*/
                String message = intent.getStringExtra("message");
                sendMessageToServer(message, false);
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
        mBtnSend = (Button) findViewById(R.id.chat_send);
        mInputMsg = (EditText) findViewById(R.id.chat_input);
        utils = new MessageUtil(getApplicationContext());
        listMessages = new ArrayList<Message>();
//        adapter = new ChatListAdapter(this, listMessages);
//        listViewMessages = (ListView) findViewById(R.id.chat_msg);
//        listViewMessages.setAdapter(adapter);

    }

    private void initToolbar() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.chat_toolbar);
        final TextView title = (TextView) findViewById(R.id.chat_title);
        title.setText("Name");
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initProfile() {
//        Intent intent = getIntent();
//        name = intent.getStringExtra("name");
    }

    private void loadHistory() {
    }

    private void sendMessageToServer(String message, boolean isSelf) {
        //TODO: to send message to web server

        Message msg;
        if (isSelf) {
            msg = new Message("Dyuha", mInputMsg.getText().toString().trim(), true);
        } else {
            msg = new Message("Dyuha", message, false);
        }
        appendMessage(msg);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //TODO: Disconnect
    }

    /**
     * Appending message to list view
     */
    private void appendMessage(final Message m) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                listMessages.add(m);

                adapter.notifyDataSetChanged();

                // Playing device's notification
//                playBeep();
            }
        });
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
            Call<SendMessageResponse> call = mService.sendMessage("userId", query);
            call.enqueue(new Callback<SendMessageResponse>() {
                @Override
                public void onResponse(Response<SendMessageResponse> response, Retrofit retrofit) {
                    if (response.body().success) {
                        sendMessageToServer(query.message, true);
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

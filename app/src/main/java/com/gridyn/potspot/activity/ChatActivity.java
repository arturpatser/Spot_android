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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.gridyn.potspot.Constant;
import com.gridyn.potspot.MessageUtil;
import com.gridyn.potspot.Person;
import com.gridyn.potspot.R;
import com.gridyn.potspot.adapter.ChatAdapter;
import com.gridyn.potspot.model.Message;
import com.gridyn.potspot.query.SendMessageQuery;
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

public class ChatActivity extends AppCompatActivity {
    private Button mBtnSend;
    private EditText mInputMsg;

    // Chat messages list adapter
    private ChatAdapter adapter;
    private List<Message> listMessages;
    private ListView listViewMessages;

    private MessageUtil utils;

    private String mNameUser;
    private String mNameSpot;

    private ChatService mService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        initRetrofit();
        initFields();
        initHeader();
        initRecyclerView();
        initToolbar();
        loadHistory();
        receiveMessage();
    }

    private void initHeader() {
        final View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.header_chat, (ViewGroup) findViewById(R.id.chat_header), false);
        final TextView nameUser = (TextView) view.findViewById(R.id.ch_h_name);
        final TextView nameSpot = (TextView) view.findViewById(R.id.ch_h_about);
        nameUser.setText(mNameUser);
        nameSpot.setText(mNameSpot);
        Picasso.with(getApplicationContext())
                .load(Constant.URL_IMAGE + getIntent().getExtras().getString("imgSpot"))
                .into((ImageView) view.findViewById(R.id.ch_h_back));
        Picasso.with(getApplicationContext())
                .load(Constant.URL_IMAGE + getIntent().getExtras().getString("imgUser"))
                .into((ImageView) view.findViewById(R.id.ch_h_img));
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

        Bundle args = getIntent().getExtras();
        mNameUser = args.getString("userName");
        mNameSpot = args.getString("spotName");

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
    }

    private void sendMessageToServer(String message, boolean isSelf) {
        Message msg;
        if (isSelf) {
            msg = new Message("Dyuha", mInputMsg.getText().toString().trim(), true);
        } else {
            msg = new Message(mNameUser, message, false);
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
            Call<MessageSendResponse> call = mService.sendMessage("userId", query);
            call.enqueue(new Callback<MessageSendResponse>() {
                @Override
                public void onResponse(Response<MessageSendResponse> response, Retrofit retrofit) {
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

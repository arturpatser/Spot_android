package com.gridyn.potspot.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gridyn.potspot.Constant;
import com.gridyn.potspot.R;
import com.gridyn.potspot.model.Message;

import java.util.List;

public class ChatListAdapter extends BaseAdapter {

    private Activity context;
    private List<Message> messagesItems;
    private TextView mName;
    private TextView mMessage;

    public ChatListAdapter(Activity context, List<Message> navDrawerItems) {
        this.context = context;
        this.messagesItems = navDrawerItems;
    }

    @Override
    public int getCount() {
        return messagesItems.size();
    }

    @Override
    public Object getItem(int position) {
        return messagesItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        /**
         * The following list not implemented reusable list items as list items
         * are showing incorrect data Add the solution if you have one
         * */

        Message m = messagesItems.get(position);

        // Identifying the message owner
        if (messagesItems.get(position).isSelf()) {
            // message belongs to you, so load the right aligned layout
//            convertView = mInflater.inflate(R.layout.item_chat_right, null);
            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.item_chat_right, (ViewGroup) context.findViewById(R.id.chat_right), false);

//            mName = (TextView) convertView.findViewById(R.id.ch_r_from);
            mMessage = (TextView) convertView.findViewById(R.id.ch_r_msg);
            Log.i(Constant.LOG, "getView: true");

        } else {
            // message belongs to other person, load the left aligned layout
//            convertView = mInflater.inflate(R.layout.item_chat_right, null);

            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.item_chat_left, (ViewGroup) context.findViewById(R.id.chat_left), false);
//            mName = (TextView) convertView.findViewById(R.id.ch_l_from);
            mMessage = (TextView) convertView.findViewById(R.id.ch_l_msg);
            Log.i(Constant.LOG, "getView: false");

        }

        mMessage.setText(m.getMessage());
//        mName.setText(m.getFromName());

        return convertView;
    }
}
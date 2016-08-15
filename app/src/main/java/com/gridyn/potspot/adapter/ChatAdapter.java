package com.gridyn.potspot.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gridyn.potspot.R;
import com.gridyn.potspot.model.Message;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.Holder> {

    private Activity context;
    private List<Message> messagesItems;
    private TextView mName;
    private TextView mMessage;

    public ChatAdapter(Activity context, List<Message> navDrawerItems) {
        this.context = context;
        this.messagesItems = navDrawerItems;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (messagesItems.get(messagesItems.size() - 1).isSelf()) {
            view = LayoutInflater.from(context)
                    .inflate(R.layout.item_chat_right, (ViewGroup) context.findViewById(R.id.chat_right), false);
            mName = (TextView) view.findViewById(R.id.ch_r_from);
            mMessage = (TextView) view.findViewById(R.id.ch_r_msg);

        } else {
            view = LayoutInflater.from(context)
                    .inflate(R.layout.item_chat_left, (ViewGroup) context.findViewById(R.id.chat_left), false);
            mName = (TextView) view.findViewById(R.id.ch_l_from);
            mMessage = (TextView) view.findViewById(R.id.ch_l_msg);
        }
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        mName.setText(messagesItems.get(position).getFromName());
        mMessage.setText(messagesItems.get(position).getMessage());
    }

    @Override
    public int getItemCount() {
        return messagesItems.size();
    }

    public static class Holder extends RecyclerView.ViewHolder {
        public Holder(View itemView) {
            super(itemView);
        }
    }
}

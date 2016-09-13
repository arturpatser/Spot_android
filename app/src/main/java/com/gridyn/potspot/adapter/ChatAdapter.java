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

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int TYPE_LEFT = 1;
    public static final int TYPE_RIGHT = 0;
    private Activity context;
    private List<Message> messagesItems;

    public ChatAdapter(Activity context, List<Message> navDrawerItems) {
        this.context = context;
        this.messagesItems = navDrawerItems;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == TYPE_RIGHT) {
            view = LayoutInflater.from(context)
                    .inflate(R.layout.item_chat_right, (ViewGroup) context.findViewById(R.id.chat_right), false);
            return new RightViewHolder(view);
        } else if (viewType == TYPE_LEFT) {
            view = LayoutInflater.from(context)
                    .inflate(R.layout.item_chat_left, (ViewGroup) context.findViewById(R.id.chat_left), false);
            return new LeftViewHolder(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof RightViewHolder) {
            ((RightViewHolder) holder).name.setText(messagesItems.get(position).getFromName());
            ((RightViewHolder) holder).message.setText(messagesItems.get(position).getMessage());
        } else {
            ((LeftViewHolder) holder).name.setText(messagesItems.get(position).getFromName());
            ((LeftViewHolder) holder).message.setText(messagesItems.get(position).getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return messagesItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        return messagesItems.get(position).isSelf() ? TYPE_RIGHT : TYPE_LEFT;
    }

    public static class RightViewHolder extends RecyclerView.ViewHolder {

        private TextView name, message;

        public RightViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.ch_r_from);
            message = (TextView) itemView.findViewById(R.id.ch_r_msg);
        }
    }

    public static class LeftViewHolder extends RecyclerView.ViewHolder {

        private TextView name, message;

        public LeftViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.ch_l_from);
            message = (TextView) itemView.findViewById(R.id.ch_l_msg);
        }
    }
}

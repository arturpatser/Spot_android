package com.gridyn.potspot.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gridyn.potspot.Constant;
import com.gridyn.potspot.R;
import com.gridyn.potspot.activity.ChatActivity;
import com.gridyn.potspot.model.Message;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.Holder> {

    private final List<Message> mMessageList;
    private final Context mContext;
    private final FragmentActivity mFragmentActivity;

    public MessageAdapter(List<Message> messages, Context context, FragmentActivity activity) {
        mMessageList = messages;
        mContext = context;
        mFragmentActivity = activity;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_message, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        final Message message = mMessageList.get(position);

        Log.i(Constant.LOG, "imageOfUser: " + message.getImgUser());
//        Picasso.with(mContext)
//                .load()
//                .into();

        holder.name.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "fonts/Roboto-Regular.ttf"));
        holder.description.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "fonts/Roboto-Medium.ttf"));
        holder.lastMessage.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "fonts/Roboto-Regular.ttf"));
        holder.date.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "fonts/Roboto-Medium.ttf"));

        holder.name.setText(message.getFromName());
        holder.description.setText(message.getSpotName());
        holder.lastMessage.setText(message.getMessage());
        holder.messageItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(mContext, ChatActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("spotName", message.getSpotName());
                intent.putExtra("fromName", message.getFromName());
                intent.putExtra("imgSpot", message.getImgSpot());
                intent.putExtra("imgUser", message.getImgUser());
                mContext.startActivity(intent);
            }
        });
        holder.date.setText(message.getDate());
    }

    @Override
    public int getItemCount() {
        return mMessageList.size();
    }

    public static class Holder extends RecyclerView.ViewHolder {

        private CircleImageView image;
        private TextView name;
        private TextView description;
        private TextView lastMessage;
        private TextView date;
        private RelativeLayout messageItem;

        public Holder(View itemView) {
            super(itemView);
            image = (CircleImageView) itemView.findViewById(R.id.message_image);
            name = (TextView) itemView.findViewById(R.id.message_name);
            description = (TextView) itemView.findViewById(R.id.message_description);
            lastMessage = (TextView) itemView.findViewById(R.id.message_last);
            date = (TextView) itemView.findViewById(R.id.message_time);
            messageItem = (RelativeLayout) itemView.findViewById(R.id.message_item);
        }
    }

}

package com.gridyn.potspot.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gridyn.potspot.R;
import com.gridyn.potspot.Spot;
import com.gridyn.potspot.activity.ChatActivity;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.Holder> {

    private final List<Spot> mSpotList;
    private final Context mContext;
    private final FragmentActivity mFragmentActivity;

    public MessageAdapter(List<Spot> spotList, Context context, FragmentActivity activity) {
        mSpotList = spotList;
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
        Spot spot = mSpotList.get(position);

        holder.name.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "fonts/Roboto-Regular.ttf"));
        holder.description.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "fonts/Roboto-Medium.ttf"));
        holder.lastMessage.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "fonts/Roboto-Regular.ttf"));
        holder.time.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "fonts/Roboto-Medium.ttf"));

        holder.name.setText(spot.getName());
        holder.description.setText(spot.getDescription());
        holder.lastMessage.setText("test");
        holder.messageItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(mContext, ChatActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mSpotList.size();
    }

    public static class Holder extends RecyclerView.ViewHolder {

        private CircleImageView image;
        private TextView name;
        private TextView description;
        private TextView lastMessage;
        private TextView time;
        private RelativeLayout messageItem;

        public Holder(View itemView) {
            super(itemView);
            image = (CircleImageView) itemView.findViewById(R.id.message_image);
            name = (TextView) itemView.findViewById(R.id.message_name);
            description = (TextView) itemView.findViewById(R.id.message_description);
            lastMessage = (TextView) itemView.findViewById(R.id.message_last);
            time = (TextView) itemView.findViewById(R.id.message_time);
            messageItem = (RelativeLayout) itemView.findViewById(R.id.message_item);
        }
    }

}

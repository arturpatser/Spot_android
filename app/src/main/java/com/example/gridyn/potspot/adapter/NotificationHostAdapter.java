package com.example.gridyn.potspot.adapter;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.gridyn.potspot.AssetsHelper;
import com.example.gridyn.potspot.R;
import com.example.gridyn.potspot.Spot;
import com.google.android.gms.wearable.Asset;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class NotificationHostAdapter extends RecyclerView.Adapter<NotificationHostAdapter.Holder> {

    private final List<Spot> mSpotList;
    private final Context mContext;
    private final FragmentActivity mFragmentActivity;

    public NotificationHostAdapter(List<Spot> spotList, Context context, FragmentActivity activity) {
        mSpotList = spotList;
        mContext = context;
        mFragmentActivity = activity;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.item_notification_host, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        Spot spot = mSpotList.get(position);
        AssetManager asset = mContext.getAssets();

        holder.back.setBackground(AssetsHelper.loadImageFromAsset(mContext, spot.getImage()));
        holder.name.setText(spot.getName());
        holder.description.setText(spot.getDescription());

        holder.name.setTypeface(Typeface.createFromAsset(asset, "fonts/Roboto-Medium.ttf"));
        holder.description.setTypeface(Typeface.createFromAsset(asset, "fonts/Roboto-Regular.ttf"));
        holder.time.setTypeface(Typeface.createFromAsset(asset, "fonts/Roboto-Medium.ttf"));
        holder.details.setTypeface(Typeface.createFromAsset(asset, "fonts/Roboto-Medium.ttf"));
        holder.accept.setTypeface(Typeface.createFromAsset(asset, "fonts/Roboto-Medium.ttf"));
        holder.reject.setTypeface(Typeface.createFromAsset(asset, "fonts/Roboto-Medium.ttf"));
    }

    @Override
    public int getItemCount() {
        return mSpotList.size();
    }

    public static class Holder extends RecyclerView.ViewHolder {

        private TextView name;
        private TextView description;
        private TextView time;
        private Button details;
        private Button accept;
        private Button reject;
        private CardView cardView;
        private LinearLayout back;
        private CircleImageView image;

        public Holder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.notification_host_name);
            description = (TextView) itemView.findViewById(R.id.notification_host_description);
            cardView = (CardView) itemView.findViewById(R.id.notification_host_card);
            back = (LinearLayout) itemView.findViewById(R.id.notification_host_back);
            time = (TextView) itemView.findViewById(R.id.notification_host_time);
            details = (Button) itemView.findViewById(R.id.notification_host_details);
            accept = (Button) itemView.findViewById(R.id.notification_host_accept);
            reject = (Button) itemView.findViewById(R.id.notification_host_reject);
            image = (CircleImageView) itemView.findViewById(R.id.profile_image);
        }
    }
}

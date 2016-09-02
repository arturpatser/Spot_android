package com.gridyn.potspot.adapter;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gridyn.potspot.AssetsHelper;
import com.gridyn.potspot.R;
import com.gridyn.potspot.response.UserInfoResponse;

import java.util.ArrayList;
import java.util.List;

public class PaidSpotsAdapter extends RecyclerView.Adapter<PaidSpotsAdapter.Holder> {

    private List<UserInfoResponse.Message.Data.Spot> mSpotList;
    private final Context mContext;
    private final FragmentActivity mFragmentActivity;

    public PaidSpotsAdapter(Context context, FragmentActivity activity) {
        mContext = context;
        mFragmentActivity = activity;

        mSpotList = new ArrayList<>();
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.item_paid_spot, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
//        Spot spot = mSpotList.get(position);

        holder.imageView.setImageDrawable(AssetsHelper.loadImageFromAsset(mContext, "images/mountain.jpg"));
    }

    @Override
    public int getItemCount() {
        return mSpotList.size();
    }

    public void addAll(List<UserInfoResponse.Message.Data.Spot> message) {

        mSpotList.addAll(message);
        notifyDataSetChanged();
    }

    public static class Holder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private TextView description;
        private TextView from;
        private TextView price;
        private TextView date;

        public Holder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.paid_image);
            description = (TextView) itemView.findViewById(R.id.paid_desc);
            from = (TextView) itemView.findViewById(R.id.paid_from);
            price = (TextView) itemView.findViewById(R.id.paid_price);
            date = (TextView) itemView.findViewById(R.id.paid_date);
        }
    }
}

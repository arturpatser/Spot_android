package com.example.gridyn.potspot.adapter;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gridyn.potspot.AssetsHelper;
import com.example.gridyn.potspot.R;
import com.example.gridyn.potspot.Spot;

import java.util.List;

public class PaidSpotsAdapter extends RecyclerView.Adapter<PaidSpotsAdapter.Holder> {

    private final List<Spot> mSpotList;
    private final Context mContext;
    private final FragmentActivity mFragmentActivity;

    public PaidSpotsAdapter(List<Spot> spotList, Context context, FragmentActivity activity) {
        mSpotList = spotList;
        mContext = context;
        mFragmentActivity = activity;
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
        return 1;
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

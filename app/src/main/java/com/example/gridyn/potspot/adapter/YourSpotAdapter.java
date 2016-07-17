package com.example.gridyn.potspot.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gridyn.potspot.Constant;
import com.example.gridyn.potspot.R;
import com.example.gridyn.potspot.Spot;
import com.squareup.picasso.Picasso;

import java.util.List;

public class YourSpotAdapter extends RecyclerView.Adapter<YourSpotAdapter.Holder> {

    private List<Spot> mSpot;
    private Context mContext;

    public YourSpotAdapter(List<Spot> mSpot, Context mContext) {
        this.mSpot = mSpot;
        this.mContext = mContext;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_your_spot, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        Spot spot = mSpot.get(position);

        Picasso.with(mContext).load(Constant.URL_IMAGE + spot.getImage()).into(holder.background);
        holder.name.setText(spot.getName());
        holder.address.setText(spot.getAddress());
    }

    @Override
    public int getItemCount() {
        return mSpot.size();
    }

    public static class Holder extends RecyclerView.ViewHolder {

        private ImageView background;
        private TextView name;
        private TextView address;

        public Holder(View itemView) {
            super(itemView);
            background = (ImageView) itemView.findViewById(R.id.your_spot_back);
            name = (TextView) itemView.findViewById(R.id.your_spot_name);
            address = (TextView) itemView.findViewById(R.id.your_spot_address);
        }
    }
}
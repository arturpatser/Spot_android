package com.example.gridyn.potspot.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.CardView;
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

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.Holder>{

    private final List<Spot> mSpotList;
    private final Context mContext;
    private final FragmentActivity mFragmentActivity;

    public HomeAdapter(List<Spot> spotList, Context context, FragmentActivity activity) {
        mSpotList = spotList;
        mContext = context;
        mFragmentActivity = activity;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_home, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, final int position) {
        Spot spot = mSpotList.get(position);

        holder.mTitle.setText(spot.getTitle());
        holder.mTypeListing.setText(spot.getTypeListing());
        holder.mPrice.setText(" " + spot.getPrice().toString() + " $ ");

        holder.mTitle.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "fonts/Roboto-Regular.ttf"));
        holder.mTypeListing.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "fonts/Roboto-Regular.ttf"));
        holder.mPrice.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "fonts/Roboto-Medium.ttf"));

        Picasso.with(mContext)
                .load(Constant.URL_IMAGE + spot.getImage())
                .into(holder.mHeader);
    }

    @Override
    public int getItemCount() {
        return mSpotList.size();
    }

    public static class Holder extends RecyclerView.ViewHolder {

        private TextView mTitle;
        private TextView mTypeListing;
        private TextView mPrice;
        private CardView mCardView;
        private ImageView mHeader;

        public Holder(View itemView) {
            super(itemView);
            mTitle = (TextView) itemView.findViewById(R.id.home_title);
            mTypeListing = (TextView) itemView.findViewById(R.id.home_type_listing);
            mPrice = (TextView) itemView.findViewById(R.id.home_price);
            mHeader = (ImageView) itemView.findViewById(R.id.home_post);
            mCardView = (CardView) itemView.findViewById(R.id.home_card);
        }
    }
}

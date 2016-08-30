package com.gridyn.potspot.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.SupportMapFragment;
import com.gridyn.potspot.Constant;
import com.gridyn.potspot.R;
import com.gridyn.potspot.Spot;
import com.gridyn.potspot.activity.DescriptionSpotActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int TYPE_MAP = 0;
    private final int TYPE_ITEM = 1;
    private final List<Spot> mSpotList;
    private final Context mContext;
    private final FragmentManager mFragmentManager;

    public HomeAdapter(List<Spot> spotList, Context context, FragmentManager activity) {
        mSpotList = spotList;
        mContext = context;
        mFragmentManager = activity;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_MAP:
//
                View map = LayoutInflater.from(mContext)
                        .inflate(R.layout.home_map, parent, false);
                return new Map(map, mFragmentManager);

            case TYPE_ITEM:

                View spots = LayoutInflater.from(mContext)
                        .inflate(R.layout.item_home, parent, false);
                return new Holder(spots);

        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof Holder) {
            final Spot spot = mSpotList.get(position - 1);
            ((Holder) holder).mTitle.setText(spot.getTitle());
            ((Holder) holder).mTypeListing.setText(spot.getTypeListing());
            ((Holder) holder).mPrice.setText(" " + spot.getPrice().toString() + " $ ");

            ((Holder) holder).mTitle.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "fonts/Roboto-Regular.ttf"));
            ((Holder) holder).mTypeListing.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "fonts/Roboto-Regular.ttf"));
            ((Holder) holder).mPrice.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "fonts/Roboto-Medium.ttf"));

            Picasso.with(mContext)
                    .load(Constant.URL_IMAGE + spot.getImage())
                    .into(((Holder) holder).mHeader);

            ((Holder) holder).mCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i(Constant.LOG, "HomeAdapter: " + position);
                    final Intent intent = new Intent(mContext, DescriptionSpotActivity.class);
                    intent.putExtra("id", spot.getId());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(intent);
                }
            });
        } else if (holder instanceof Map) {
//            ((Map) holder).mapFragment.getMapAsync(new OnMapReadyCallback() {
//                @Override
//                public void onMapReady(GoogleMap map) {
//                    LatLng toronto = new LatLng(43.453505, 80.465284);
//                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(toronto, 11));
////                    setMarkers(map);
//                }
//            });
        }
    }

    @Override
    public int getItemCount() {
        return mSpotList.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? TYPE_MAP : TYPE_ITEM;
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

    public static class Map extends RecyclerView.ViewHolder {

        private SupportMapFragment mapFragment;

        public Map(View itemView) {
            super(itemView);
        }

        public Map(View itemView, FragmentManager fragmentManager) {
            super(itemView);
            mapFragment = (SupportMapFragment) fragmentManager.findFragmentById(R.id.map);
        }
    }
}
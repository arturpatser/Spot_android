package com.example.gridyn.potspot.adapter;

import android.app.FragmentManager;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
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

public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.Holder>{

    private final List<Spot> mSpotList;
    private final Context mContext;
    private final FragmentManager mFragment;

    public SearchResultAdapter(List<Spot> spots, Context context, FragmentManager fragmentActivity) {
        mSpotList = spots;
        mContext = context;
        mFragment = fragmentActivity;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.item_search_result, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        Spot spot = mSpotList.get(position);
        AssetManager asset = mContext.getAssets();

        holder.imgTitle.setImageDrawable(AssetsHelper
                .loadImageFromAsset(mContext, spot.getImage()));
        holder.price.setText("$" + spot.getPrice());
        holder.tvUp.setText(spot.getTypeListing());
        holder.tvDown.setText(spot.getTitle() + " | " + spot.getAddress());

        holder.price.setTypeface(Typeface.createFromAsset(asset, "fonts/Roboto-Regular.ttf"));
        holder.tvUp.setTypeface(Typeface.createFromAsset(asset, "fonts/Roboto-Regular.ttf"));
        holder.tvDown.setTypeface(Typeface.createFromAsset(asset, "fonts/Roboto-Regular.ttf"));
    }

    @Override
    public int getItemCount() {
        return mSpotList.size();
    }

    public static class Holder extends RecyclerView.ViewHolder {

        private ImageView imgTitle;
        private TextView price;
        private TextView tvUp;
        private TextView tvDown;

        public Holder(View itemView) {
            super(itemView);
            imgTitle = (ImageView) itemView.findViewById(R.id.sch_res_img);
            price = (TextView) itemView.findViewById(R.id.sch_res_price);
            tvUp = (TextView) itemView.findViewById(R.id.sch_res_tv_up);
            tvDown = (TextView) itemView.findViewById(R.id.sch_res_tv_down);
        }
    }
}

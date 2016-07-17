package com.gridyn.potspot.adapter;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gridyn.potspot.Constant;
import com.gridyn.potspot.R;
import com.gridyn.potspot.activity.DescriptionSpotActivity;
import com.gridyn.potspot.response.SpotSearchResponse;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.Holder> {

    private final List<SpotSearchResponse.Spots> mSpotList;
    private final Context mContext;
    private final FragmentManager mFragment;

    public SearchResultAdapter(List<SpotSearchResponse.Spots> spots, Context context, FragmentManager fragmentActivity) {
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
        final SpotSearchResponse.Spots spot = mSpotList.get(position);
        AssetManager asset = mContext.getAssets();

        Picasso.with(mContext)
                .load(Constant.URL_IMAGE + spot.data.imgs.get(0))
                .into(holder.imgTitle);

        Picasso.with(mContext)
                .load(Constant.URL_IMAGE + spot.data.userImgs.get(0))
                .into(holder.avatar);

        holder.price.setText("$" + spot.data.price);
        holder.tvUp.setText(spot.data.type);
        holder.tvDown.setText(spot.data.type + " | " + spot.data.address);

        holder.price.setTypeface(Typeface.createFromAsset(asset, "fonts/Roboto-Regular.ttf"));
        holder.tvUp.setTypeface(Typeface.createFromAsset(asset, "fonts/Roboto-Regular.ttf"));
        holder.tvDown.setTypeface(Typeface.createFromAsset(asset, "fonts/Roboto-Regular.ttf"));

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(mContext, DescriptionSpotActivity.class);
                intent.putExtra("id", spot.id.$id);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mSpotList.size();
    }

    public static class Holder extends RecyclerView.ViewHolder {

        private CircleImageView avatar;
        private ImageView imgTitle;
        private TextView price;
        private TextView tvUp;
        private TextView tvDown;
        private CardView card;

        public Holder(View itemView) {
            super(itemView);
            card = (CardView) itemView.findViewById(R.id.sch_res_card);
            avatar = (CircleImageView) itemView.findViewById(R.id.sch_res_avatar);
            imgTitle = (ImageView) itemView.findViewById(R.id.sch_res_img);
            price = (TextView) itemView.findViewById(R.id.sch_res_price);
            tvUp = (TextView) itemView.findViewById(R.id.sch_res_tv_up);
            tvDown = (TextView) itemView.findViewById(R.id.sch_res_tv_down);
        }
    }
}

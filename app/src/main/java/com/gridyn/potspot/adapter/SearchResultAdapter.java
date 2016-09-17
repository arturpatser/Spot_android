package com.gridyn.potspot.adapter;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gridyn.potspot.Constant;
import com.gridyn.potspot.Person;
import com.gridyn.potspot.R;
import com.gridyn.potspot.activity.DescriptionSpotActivity;
import com.gridyn.potspot.activity.SpaceActivity;
import com.gridyn.potspot.response.SpotSearchResponse;
import com.gridyn.potspot.response.SuccessResponse;
import com.gridyn.potspot.utils.ServerApiUtil;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class SearchResultAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_BANNER = 1;
    private static final int TYPE_ITEM = 0;
    private static final String TAG = SearchResultAdapter.class.getName();
    private final List<SpotSearchResponse.Spots> mSpotList;
    private final Context mContext;
    private final FragmentManager mFragment;
    RecyclerView parent;

    public SearchResultAdapter(List<SpotSearchResponse.Spots> spots, Context context,
                               FragmentManager fragmentActivity, RecyclerView recyclerView) {
        mSpotList = spots;
        mContext = context;
        mFragment = fragmentActivity;
        this.parent = recyclerView;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType) {

            case TYPE_ITEM:

                View view = LayoutInflater.from(mContext)
                        .inflate(R.layout.item_search_result, parent, false);
                return new Holder(view);

            case TYPE_BANNER:

                View banner = LayoutInflater.from(mContext)
                        .inflate(R.layout.item_layout_banner, parent, false);

                return new Banner(banner);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof Holder) {

            int pos = position;

            if (!Person.isHost() && position > 3)
                pos--;

            final SpotSearchResponse.Spots spot = mSpotList.get(pos);
            AssetManager asset = mContext.getAssets();

        if (spot.data.imgs.size() != 0) {
            Picasso.with(mContext)
                    .load(Constant.URL_IMAGE + spot.data.imgs.get(0))
                    .into(((Holder) holder).imgTitle);
        } else {
            Picasso.with(mContext)
                    .load(Constant.BASE_IMAGE)
                    .into(((Holder) holder).imgTitle);
        }

        if (spot.data.userImgs.size() != 0) {
            Picasso.with(mContext)
                    .load(Constant.URL_IMAGE + spot.data.userImgs.get(0))
                    .into(((Holder) holder).avatar);
        } else {
            Picasso.with(mContext)
                    .load(Constant.BASE_IMAGE)
                    .into(((Holder) holder).avatar);
        }

        ((Holder) holder).price.setText("$" + (spot.data.price / 100));
        ((Holder) holder).tvUp.setText(spot.data.name);
            ((Holder) holder).tvDown.setText(spot.data.type + " | " + spot.data.address);

            ((Holder) holder).price.setTypeface(Typeface.createFromAsset(asset, "fonts/Roboto-Regular.ttf"));
            ((Holder) holder).tvUp.setTypeface(Typeface.createFromAsset(asset, "fonts/Roboto-Regular.ttf"));
            ((Holder) holder).tvDown.setTypeface(Typeface.createFromAsset(asset, "fonts/Roboto-Regular.ttf"));

            ((Holder) holder).card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Intent intent = new Intent(mContext, DescriptionSpotActivity.class);
                    intent.putExtra("id", spot.id.$id);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(intent);
                }
            });

            if (spot.data.inFavorites) {

                ((Holder) holder).favorite.setImageResource(R.drawable.ic_favorite_black_24dp);

            } else {

                ((Holder) holder).favorite.setImageResource(R.drawable.ic_favorite_border_black_24dp);
            }

            ((Holder) holder).favorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (spot.data.inFavorites) {

                        Call<SuccessResponse> remove = ServerApiUtil.initSpot().deleteFromFavorite(spot.id.$id, Person.getTokenMap());
                        remove.enqueue(new Callback<SuccessResponse>() {
                            @Override
                            public void onResponse(Response<SuccessResponse> response, Retrofit retrofit) {

                                spot.data.inFavorites = false;
                                updateSpot(spot);
                            }

                            @Override
                            public void onFailure(Throwable t) {

                                Log.e(TAG, "onFailure: error while remove from fav = " + Log.getStackTraceString(t));
                            }
                        });
                    } else {

                        Call<SuccessResponse> add = ServerApiUtil.initSpot().addToFavorite(spot.id.$id, Person.getTokenMap());

                        add.enqueue(new Callback<SuccessResponse>() {
                            @Override
                            public void onResponse(Response<SuccessResponse> response, Retrofit retrofit) {

                                spot.data.inFavorites = true;
                                updateSpot(spot);
                            }

                            @Override
                            public void onFailure(Throwable t) {

                                Log.e(TAG, "onFailure: error while remove from fav = " + Log.getStackTraceString(t));
                            }
                        });
                    }
                }
            });
        }

        if (holder instanceof Banner) {

            ((Banner) holder).becomeHost.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (!Person.isHost()) {
                        //TODO show verification window here
//                        Snackbar.make(parent, "Your account is not verified", Snackbar.LENGTH_INDEFINITE)
//                                .setAction("goto verify", new View.OnClickListener() {
//                                    @Override
//                                    public void onClick(View v) {
//                                        final Intent intent = new Intent(mContext, VerificationActivity.class);
//                                        mContext.startActivity(intent);
//                                    }
//                                })
//                                .setActionTextColor(mContext.getResources().getColor(R.color.mainRed))
//                                .show();
                    } else if (Person.isHost()) {
                        Intent intent = new Intent(mContext, SpaceActivity.class);
                        mContext.startActivity(intent);
                    }
                }
            });
        }
    }

    private void updateSpot(SpotSearchResponse.Spots spot) {

        int position = -1;

        for (int i = 0; i < mSpotList.size(); i++) {

            SpotSearchResponse.Spots spot1 = mSpotList.get(i);
            if (spot1.id.$id.equals(spot.id.$id))
                position = i;
        }

        if (position != -1) {
            mSpotList.set(position, spot);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemViewType(int position) {

        if (mSpotList.size() > 2 && position == 1 && !Person.isHost())
            return TYPE_BANNER;

        return TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return (mSpotList.size() > 2 && !Person.isHost()) ? mSpotList.size() + 1 : mSpotList.size();
    }

    public static class Holder extends RecyclerView.ViewHolder {

        private CircleImageView avatar;
        private ImageView imgTitle;
        private TextView price;
        private TextView tvUp;
        private TextView tvDown;
        private CardView card;
        private ImageView favorite;

        public Holder(View itemView) {
            super(itemView);
            card = (CardView) itemView.findViewById(R.id.sch_res_card);
            avatar = (CircleImageView) itemView.findViewById(R.id.sch_res_avatar);
            imgTitle = (ImageView) itemView.findViewById(R.id.sch_res_img);
            price = (TextView) itemView.findViewById(R.id.sch_res_price);
            tvUp = (TextView) itemView.findViewById(R.id.sch_res_tv_up);
            tvDown = (TextView) itemView.findViewById(R.id.sch_res_tv_down);
            favorite = (ImageView) itemView.findViewById(R.id.favorite);
        }
    }

    private class Banner extends RecyclerView.ViewHolder {

        TextView becomeHost;

        public Banner(View view) {
            super(view);

            becomeHost = (TextView) view.findViewById(R.id.tv_become_host);
        }
    }
}

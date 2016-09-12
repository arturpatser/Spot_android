package com.gridyn.potspot.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
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

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.gridyn.potspot.Constant;
import com.gridyn.potspot.Person;
import com.gridyn.potspot.R;
import com.gridyn.potspot.Spot;
import com.gridyn.potspot.activity.DescriptionSpotActivity;
import com.gridyn.potspot.response.SuccessResponse;
import com.gridyn.potspot.utils.ServerApiUtil;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = HomeAdapter.class.getName();
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

                return new Map(map);

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

                    showItem(spot);
                }
            });

            if (spot.isInFavorites()) {

                ((Holder) holder).favorite.setImageResource(R.drawable.ic_favorite_black_24dp);

            } else {

                ((Holder) holder).favorite.setImageResource(R.drawable.ic_favorite_border_black_24dp);
            }

            ((Holder) holder).favorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (spot.isInFavorites()) {

                        Call<SuccessResponse> remove = ServerApiUtil.initSpot().deleteFromFavorite(spot.getId(), Person.getTokenMap());
                        remove.enqueue(new Callback<SuccessResponse>() {
                            @Override
                            public void onResponse(Response<SuccessResponse> response, Retrofit retrofit) {

                                spot.setInFavorites(false);
                                updateSpot(spot);
                            }

                            @Override
                            public void onFailure(Throwable t) {

                                Log.e(TAG, "onFailure: error while remove from fav = " + Log.getStackTraceString(t));
                            }
                        });
                    } else {

                        Call<SuccessResponse> add = ServerApiUtil.initSpot().addToFavorite(spot.getId(), Person.getTokenMap());

                        add.enqueue(new Callback<SuccessResponse>() {
                            @Override
                            public void onResponse(Response<SuccessResponse> response, Retrofit retrofit) {

                                spot.setInFavorites(true);
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

        } else if (holder instanceof Map) {

            ((Map) holder).mapView.onCreate(null);
            ((Map) holder).mapView.onResume();
            ((Map) holder).mapView.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap googleMap) {

                    LatLng toronto = new LatLng(43.7001100, -79.4163000);
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(toronto, 11));

                    for (Spot spot : mSpotList) {

                        Marker marker = googleMap.addMarker(new MarkerOptions()
                                .icon(BitmapDescriptorFactory.fromBitmap(getMarkerBitmap(spot)))
                                .position(spot.getLatLng()));
                    }

                    googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                        @Override
                        public boolean onMarkerClick(Marker marker) {

                            Spot spot = findSpotByMarker(marker);

                            if (spot != null)
                                showItem(findSpotByMarker(marker));

                            return true;
                        }
                    });
                }
            });
        }
    }

    private void updateSpot(Spot spot) {

        int position = -1;

        for (int i = 0; i < mSpotList.size(); i++) {

            Spot spot1 = mSpotList.get(i);
            if (spot1.getId().equals(spot.getId()))
                position = i;
        }

        if (position != -1) {
            mSpotList.set(position, spot);
            notifyDataSetChanged();
        }
    }

    private Bitmap getMarkerBitmap(Spot spot) {
        Bitmap.Config conf = Bitmap.Config.ARGB_8888;
        Bitmap bmp = Bitmap.createBitmap(80, 80, conf);
        Canvas canvas1 = new Canvas(bmp);

// paint defines the text color, stroke width and size
        Paint color = new Paint();
        color.setTextSize(35);
        color.setColor(Color.WHITE);

// modify canvas
        canvas1.drawBitmap(BitmapFactory.decodeResource(mContext.getResources(),
                R.drawable.home_marker), 0, 0, color);
        canvas1.drawText("$" + spot.getPrice().toString(), 30, 40, color);

        return bmp;
    }

    private Spot findSpotByMarker(Marker marker) {

        for (Spot spot : mSpotList) {

            if (spot.getLatLng().equals(marker.getPosition()))
                return spot;
        }

        return null;
    }

    private void showItem(Spot spot) {

        final Intent intent = new Intent(mContext, DescriptionSpotActivity.class);
        intent.putExtra("id", spot.getId());
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return mSpotList.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? TYPE_MAP : TYPE_ITEM;
    }

    public void updateSpot(String spotId, boolean favorite) {

        for (Spot spot : mSpotList)
            if (spot.getId().equals(spotId))
                spot.setInFavorites(favorite);

        notifyDataSetChanged();
    }

    public static class Holder extends RecyclerView.ViewHolder {

        private TextView mTitle;
        private TextView mTypeListing;
        private TextView mPrice;
        private CardView mCardView;
        private ImageView mHeader;
        private ImageView favorite;

        public Holder(View itemView) {
            super(itemView);
            mTitle = (TextView) itemView.findViewById(R.id.home_title);
            mTypeListing = (TextView) itemView.findViewById(R.id.home_type_listing);
            mPrice = (TextView) itemView.findViewById(R.id.home_price);
            mHeader = (ImageView) itemView.findViewById(R.id.home_post);
            mCardView = (CardView) itemView.findViewById(R.id.home_card);
            favorite = (ImageView) itemView.findViewById(R.id.favorite);
        }
    }

    public static class Map extends RecyclerView.ViewHolder {

        MapView mapView;

        public Map(View itemView) {
            super(itemView);

            mapView = (MapView) itemView.findViewById(R.id.map);
        }
    }
}
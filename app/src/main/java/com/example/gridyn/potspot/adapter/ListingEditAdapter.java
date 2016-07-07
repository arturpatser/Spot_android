package com.example.gridyn.potspot.adapter;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.gridyn.potspot.AssetsHelper;
import com.example.gridyn.potspot.R;
import com.example.gridyn.potspot.Spot;

import java.util.List;

public class ListingEditAdapter extends RecyclerView.Adapter<ListingEditAdapter.Holder> {

    private final List<Spot> mSpotList;
    private final Context mContext;
    private final FragmentActivity mFragment;

    public ListingEditAdapter(List<Spot> spotList, Context context, FragmentActivity fragmentManager) {
        mSpotList = spotList;
        mContext = context;
        mFragment = fragmentManager;

    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.item_listing_edit, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        final Spot spot = mSpotList.get(position);

        holder.imageView.setImageDrawable(AssetsHelper.loadImageFromAsset(mContext, spot.getImage()));
    }

    @Override
    public int getItemCount() {
        return mSpotList.size();
    }

    public static class Holder extends RecyclerView.ViewHolder {

        private ImageView imageView;

        public Holder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.list_edit_image);
        }
    }
}

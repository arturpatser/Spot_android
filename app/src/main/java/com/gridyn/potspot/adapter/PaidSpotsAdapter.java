package com.gridyn.potspot.adapter;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;
import com.gridyn.potspot.Constant;
import com.gridyn.potspot.R;
import com.gridyn.potspot.model.notificationsModels.Message;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class PaidSpotsAdapter extends RecyclerView.Adapter<PaidSpotsAdapter.Holder> {

    private List<Message> mSpotList;
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

        Message spot = mSpotList.get(position);

        if (spot.getSpot().getData().getImgs().size() > 0)
            Picasso.with(mContext)
                    .load(Constant.URL_IMAGE + spot.getSpot().getData().getImgs().get(0))
                    .into(holder.background);

        holder.description.setText(spot.getSpot().getData().getAbout());
        holder.from.setText(spot.getSpot().getData().getAddress() + ", " + spot.getSpot().getData().getCountry());
        holder.price.setText("$ " + spot.getSystem().getFullPrice() / 100);
        holder.date.setText(spot.getData().getDate() + " " + buildBookTime(spot));

        holder.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //TODO call chat here
            }
        });
    }

    private String buildBookTime(Message notif) {

        return notif.getData().getsTimeFrom() + " " + notif.getData().getsAmPmFrom() + " - " +
                notif.getData().getsTimeTo() + " " + notif.getData().getsAmPmTo();

    }

    @Override
    public int getItemCount() {
        return mSpotList.size();
    }

    public void addAll(List<Message> message) {

        mSpotList.addAll(message);
        notifyDataSetChanged();
    }

    public void clean() {

        mSpotList.clear();
        notifyDataSetChanged();
    }

    public static class Holder extends RecyclerView.ViewHolder {

        private ImageView background;
        private TextView description;
        private TextView from;
        private TextView price;
        private TextView date;
        FloatingActionButton fab;

        public Holder(View itemView) {
            super(itemView);
            background = (ImageView) itemView.findViewById(R.id.paid_image);
            description = (TextView) itemView.findViewById(R.id.paid_desc);
            from = (TextView) itemView.findViewById(R.id.paid_from);
            price = (TextView) itemView.findViewById(R.id.paid_price);
            date = (TextView) itemView.findViewById(R.id.paid_date);
            fab = (FloatingActionButton) itemView.findViewById(R.id.paid_email);
        }
    }
}

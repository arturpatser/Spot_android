package com.example.gridyn.potspot.adapter;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.gridyn.potspot.AssetsHelper;
import com.example.gridyn.potspot.R;
import com.example.gridyn.potspot.Spot;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import de.hdodenhof.circleimageview.CircleImageView;

public class NotificationClientAdapter extends RecyclerView.Adapter<NotificationClientAdapter.Holder> {

    private final List<Spot> mSpotList;
    private final Context mContext;
    private final FragmentActivity mFragmentActivity;
    private int mCurrentPeriod;
    private boolean spinBlock;

    public NotificationClientAdapter(List<Spot> spotList, Context context, FragmentActivity activity) {
        mSpotList = spotList;
        mContext = context;
        mFragmentActivity = activity;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.item_notification_client, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(final Holder holder, final int position) {
        final Spot spot = mSpotList.get(position);
        AssetManager asset = mContext.getAssets();

        holder.background.setBackground(AssetsHelper.loadImageFromAsset(mContext, spot.getImage()));
        holder.title.setText(spot.getTitle());
        holder.typeListing.setText(spot.getTypeListing());

        holder.review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "review - " + position, Snackbar.LENGTH_SHORT).show();
                if(!spinBlock) {
                    spinBlock = true;
                    runTimer(10, holder.review);
                }
            }
        });

        holder.title.setTypeface(Typeface.createFromAsset(asset, "fonts/Roboto-Medium.ttf"));
        holder.typeListing.setTypeface(Typeface.createFromAsset(asset, "fonts/Roboto-Regular.ttf"));
        holder.review.setTypeface(Typeface.createFromAsset(asset, "fonts/Roboto-Medium.ttf"));
    }

    private void runTimer(final int period, final Button button) {
        final Timer timer = new Timer();
        mCurrentPeriod = period;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            button.setBackground(mContext.getResources()
                    .getDrawable(R.drawable.btn_review_time, mContext.getTheme()));
        } else {
            button.setBackground(mContext.getResources()
                    .getDrawable(R.drawable.btn_review_time));
        }

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                mFragmentActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mCurrentPeriod--;
                        button.setText(new SimpleDateFormat("mm:ss").format(new Date(mCurrentPeriod * 1000)));
                        if(mCurrentPeriod == 0) {
                            timer.cancel();
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                button.setBackground(mContext.getResources()
                                        .getDrawable(R.drawable.btn_review_green, mContext.getTheme()));
                            } else {
                                button.setBackground(mContext.getResources()
                                        .getDrawable(R.drawable.btn_review_green));
                            }
                            button.setText(mContext.getResources().getString(R.string.review));
                            spinBlock = false;
                        }
                    }
                });
            }
        }, 0, 1000);
    }

    @Override
    public int getItemCount() {
        return mSpotList.size();
    }

    public static class Holder extends RecyclerView.ViewHolder {

        private CircleImageView image;
        private TextView title;
        private TextView typeListing;
        private CardView cardView;
        private LinearLayout background;
        private Button review;

        public Holder(View itemView) {
            super(itemView);
            image = (CircleImageView) itemView.findViewById(R.id.notification_client_profile);
            title = (TextView) itemView.findViewById(R.id.notification_client_title);
            typeListing = (TextView) itemView.findViewById(R.id.notification_client_description);
            cardView = (CardView) itemView.findViewById(R.id.notification_client_card);
            background = (LinearLayout) itemView.findViewById(R.id.notification_client_back);
            review = (Button) itemView.findViewById(R.id.notification_client_review);
        }
    }
}

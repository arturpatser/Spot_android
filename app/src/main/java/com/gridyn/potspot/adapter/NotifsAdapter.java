package com.gridyn.potspot.adapter;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
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
import com.gridyn.potspot.activity.BuySpotActivity;
import com.gridyn.potspot.databinding.ItemBookRequestBinding;
import com.gridyn.potspot.databinding.ItemBookVerifiedBinding;
import com.gridyn.potspot.model.notificationsModels.Message;
import com.gridyn.potspot.response.SuccessResponse;
import com.gridyn.potspot.utils.ServerApiUtil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by dmytro_vodnik on 9/12/16.
 * working on potspot project
 */
public class NotifsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int BOOK_VERIFIED = 0;
    private static final int BOOK_REQUEST = 1;
    private static final String TAG = NotifsAdapter.class.getName();
    LayoutInflater layoutInflater;
    Context context;
    List<Message> notifsList;

    public NotifsAdapter(Context context) {

        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.notifsList = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == BOOK_VERIFIED) {

            ItemBookVerifiedBinding itemBookVerifiedBinding = DataBindingUtil.inflate(layoutInflater,
                    R.layout.item_book_verified, parent, true);

            return new BookVirifiedHolder(itemBookVerifiedBinding);
        }

        if (viewType == BOOK_REQUEST) {

            ItemBookRequestBinding itemBookRequestBinding = DataBindingUtil.inflate(layoutInflater,
                    R.layout.item_book_request, parent, true);

            return new BookRequestHolder(itemBookRequestBinding);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        final Message notif = getItem(position);

        if (holder instanceof BookVirifiedHolder) {

            ((BookVirifiedHolder) holder).binding.setSpotName(notif.getSpot().getData().getName());
            ((BookVirifiedHolder) holder).binding.setMessage(context.getString(R.string.book_request_accepted));

            if (notif.getSpot().getData().getImgs().size() > 0)
            Picasso.with(context)
                    .load(Constant.URL_IMAGE + notif.getSpot().getData().getImgs().get(0))
                    .into(((BookVirifiedHolder) holder).spotImg);

            if (notif.getSpot().getData().getUserImgs().size() > 0)
            Picasso.with(context)
                    .load(Constant.URL_IMAGE + notif.getSpot().getData().getUserImgs().get(0))
                    .into(((BookVirifiedHolder) holder).avatar);

            ((BookVirifiedHolder) holder).available.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, BuySpotActivity.class);
                    intent.putExtra("id", notif.getSpot().getId().get$id());
                    intent.putExtra(Constant.REQUEST_ID, notif.getId().get$id());
                    intent.putExtra(Constant.OPEN_FOR_BOOK, false);
                    intent.putExtra(Constant.PARTY_SIZE, String.valueOf(notif.getData().getGuests()));
                    intent.putExtra(Constant.SPOT_PRICE, notif.getSpot().getData().getPrice());
                    context.startActivity(intent);
                }
            });
        }

        if (holder instanceof BookRequestHolder) {

            ((BookRequestHolder) holder).itemBookRequestBinding.setSpotName(notif.getSpot().getData().getName());

            if (notif.getSpot().getData().getImgs().size() > 0)
                Picasso.with(context)
                        .load(Constant.URL_IMAGE + notif.getSpot().getData().getImgs().get(0))
                        .into(((BookRequestHolder) holder).spotImg);

            ((BookRequestHolder) holder).acceptButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Call<SuccessResponse> call = ServerApiUtil.initUser().confirmBooking(notif.getId().get$id(),
                            Person.getTokenMap());

                    call.enqueue(new Callback<SuccessResponse>() {
                        @Override
                        public void onResponse(Response<SuccessResponse> response, Retrofit retrofit) {

                            SuccessResponse success = response.body();

                            Log.d(TAG, "onResponse: resp = " + success);
                        }

                        @Override
                        public void onFailure(Throwable t) {

                            Log.e(TAG, "onFailure: error while accept request = " + Log.getStackTraceString(t));
                        }
                    });
                }
            });

            ((BookRequestHolder) holder).rejectButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Call<SuccessResponse> call = ServerApiUtil.initUser().declineBooking(notif.getId().get$id(),
                            Person.getTokenMap());

                    call.enqueue(new Callback<SuccessResponse>() {
                        @Override
                        public void onResponse(Response<SuccessResponse> response, Retrofit retrofit) {

                            SuccessResponse success = response.body();

                            Log.d(TAG, "onResponse: resp = " + success);

                            if (success.isSuccess())
                                removeItem(notif, position);
                        }

                        @Override
                        public void onFailure(Throwable t) {

                            Log.e(TAG, "onFailure: error while accept request = " + Log.getStackTraceString(t));
                        }
                    });
                }
            });

            ((BookRequestHolder) holder).details.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                }
            });
            
        }
    }

    private void removeItem(Message notif, int position) {

        this.notifsList.remove(notif);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, getItemCount() - position);
    }

    @Override
    public int getItemCount() {
        return notifsList.size();
    }

    @Override
    public int getItemViewType(int position) {

        Message message = getItem(position);

        switch (message.getRequestType()) {

            case "book_verified":
                return BOOK_VERIFIED;

            case "book_request":
                return BOOK_REQUEST;
        }

        return 0;
    }

    private Message getItem(int position) {

        return this.notifsList.get(position);
    }

    public void addAll(List<Message> notifsArray) {

        this.notifsList.addAll(notifsArray);
        notifyDataSetChanged();
    }

    public void cleanArray() {

        this.notifsList.clear();
        notifyDataSetChanged();
    }

    public class BookVirifiedHolder extends RecyclerView.ViewHolder {

        ItemBookVerifiedBinding binding;

        @BindView(R.id.spot_img)
        ImageView spotImg;

        @BindView(R.id.available)
        TextView available;

        @BindView(R.id.host_avatar)
        ImageView avatar;

        public BookVirifiedHolder(ItemBookVerifiedBinding itemView) {
            super(itemView.getRoot());

            this.binding = itemView;

            ButterKnife.bind(this, binding.getRoot());
        }
    }

    public class BookRequestHolder extends RecyclerView.ViewHolder {

        ItemBookRequestBinding itemBookRequestBinding;

        @BindView(R.id.details_button)
        TextView details;

        @BindView(R.id.accept_button)
        TextView acceptButton;

        @BindView(R.id.reject_button)
        TextView rejectButton;

        @BindView(R.id.spot_img)
        ImageView spotImg;

        public BookRequestHolder(ItemBookRequestBinding itemBookRequestBinding) {
            super(itemBookRequestBinding.getRoot());

            this.itemBookRequestBinding = itemBookRequestBinding;

            ButterKnife.bind(this, itemBookRequestBinding.getRoot());
        }
    }
}

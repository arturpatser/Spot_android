package com.gridyn.potspot.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.gridyn.potspot.R;
import com.gridyn.potspot.databinding.ActivityPopupBinding;
import com.gridyn.potspot.model.notificationsModels.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dmytro_vodnik on 9/12/16.
 * working on potspot project
 */
public class NotifsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int BOOK_VERIFIED = 0;
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

            ActivityPopupBinding activityPopupBinding = DataBindingUtil.inflate(layoutInflater,
                    R.layout.activity_popup, parent, true);

            return new BookVirifiedHolder(activityPopupBinding);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        Message notif = getItem(position);

        if (holder instanceof BookVirifiedHolder) {

            ((BookVirifiedHolder) holder).binding.setSpotName(notif.getSpot().getData().getName());
            ((BookVirifiedHolder) holder).binding.setMessage(context.getString(R.string.book_request_accepted));
        }
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

    private class BookVirifiedHolder extends RecyclerView.ViewHolder {

        ActivityPopupBinding binding;

        public BookVirifiedHolder(ActivityPopupBinding itemView) {
            super(itemView.getRoot());

            this.binding = itemView;
        }
    }
}

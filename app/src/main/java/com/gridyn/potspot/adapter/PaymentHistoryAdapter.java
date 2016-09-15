package com.gridyn.potspot.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.gridyn.potspot.R;
import com.gridyn.potspot.databinding.ItemMoneyFirstHeaderBinding;
import com.gridyn.potspot.databinding.ItemMoneySecondHeaderBinding;
import com.gridyn.potspot.databinding.ItemPaymentHistoryBinding;
import com.gridyn.potspot.model.PaymentHistoryItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dmytro_vodnik on 8/23/16.
 * working on potspot project
 */
public class PaymentHistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_FIRST_HEADER = 0;
    private static final int TYPE_SECOND_HEADER = 1;
    private static final int TYPE_HISTORY_ITEM = 2;
    private final Context context;
    ArrayList<PaymentHistoryItem> paymentHistoryItems;
    LayoutInflater layoutInflater;
    private String latestPaymentDate;
    private String pendingMoney;

    public PaymentHistoryAdapter(Context context) {

        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        paymentHistoryItems = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType) {

            case TYPE_FIRST_HEADER:

                ItemMoneyFirstHeaderBinding binding = DataBindingUtil.inflate(layoutInflater,
                        R.layout.item_money_first_header, parent, true);

                return new FirstHeader(binding);

            case TYPE_SECOND_HEADER:

                ItemMoneySecondHeaderBinding sBinding = DataBindingUtil.inflate(layoutInflater,
                        R.layout.item_money_second_header, parent, true);

                return new SecondHeader(sBinding);

            case TYPE_HISTORY_ITEM:

                ItemPaymentHistoryBinding iBinding = DataBindingUtil.inflate(layoutInflater,
                        R.layout.item_payment_history, parent, true);
                return new HistoryItem(iBinding);
        }
        return null;
    }



    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof FirstHeader) {

            ((FirstHeader) holder).binding.setLatestPaymentDate(latestPaymentDate);
            ((FirstHeader) holder).binding.setPendingMoney(pendingMoney);
        } else if (holder instanceof SecondHeader) {

        } else if (holder instanceof HistoryItem) {

            // -2 because of two headers
            ((HistoryItem) holder).binding.setItem(getHistoryItem(position - 2));
        }
    }

    private PaymentHistoryItem getHistoryItem(int position) {
        return paymentHistoryItems.get(position);
    }

    @Override
    public int getItemViewType(int position) {

        if (position == 0)
            return TYPE_FIRST_HEADER;

        if (position == 1)
            return TYPE_SECOND_HEADER;

        return TYPE_HISTORY_ITEM;
    }

    @Override
    public int getItemCount() {
        //2 - two headers
        return 2 + paymentHistoryItems.size();
    }

    public void addItems(ArrayList<PaymentHistoryItem> paymentHistoryItemArrayList) {

        this.paymentHistoryItems.addAll(paymentHistoryItemArrayList);
        notifyDataSetChanged();
    }

    public void setMyMoneyDetails(String s, String s1) {

        this.latestPaymentDate = s;
        this.pendingMoney = s1;

        notifyDataSetChanged();
    }

    public void clean() {


    }

    public void addAll(List<PaymentHistoryItem> notifsArray) {

        this.paymentHistoryItems.addAll(notifsArray);
        notifyDataSetChanged();
    }

    private class FirstHeader extends RecyclerView.ViewHolder {

        private final ItemMoneyFirstHeaderBinding binding;

        public FirstHeader(ItemMoneyFirstHeaderBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
        }
    }

    private class SecondHeader extends RecyclerView.ViewHolder {

        private final ItemMoneySecondHeaderBinding binding;

        public SecondHeader(ItemMoneySecondHeaderBinding sBinding) {
            super(sBinding.getRoot());

            this.binding = sBinding;
        }
    }

    private class HistoryItem extends RecyclerView.ViewHolder {

        private final ItemPaymentHistoryBinding binding;

        public HistoryItem(ItemPaymentHistoryBinding iBinding) {
            super(iBinding.getRoot());

            this.binding = iBinding;
        }
    }
}

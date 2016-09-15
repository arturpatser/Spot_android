package com.gridyn.potspot.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;

import com.gridyn.potspot.Constant;
import com.gridyn.potspot.R;
import com.gridyn.potspot.databinding.ActivityPopupBinding;
import com.gridyn.potspot.model.AcceptRequestModel;

public class ClientPopup extends AppCompatActivity {

    ActivityPopupBinding binding;
    String spotId;
    String requestId;
    String partySize;
    int spotPrice;
    AcceptRequestModel acceptRequestModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_popup);

        Bundle args = getIntent().getExtras();

        if (args != null) {

            binding.setMessage(args.getString(Constant.NOTIF_TEXT));
            binding.setSpotName(args.getString(Constant.SPOT_NAME));

            partySize = args.getString(Constant.PARTY_SIZE);
            spotPrice = args.getInt(Constant.SPOT_PRICE);

            spotId = args.getString(Constant.SPOT_ID);
            requestId = args.getString(Constant.REQUEST_ID);

            acceptRequestModel = args.getParcelable(Constant.ACCEPT_REQ_MODEL);
        }
    }

    public void onDetailsClick(View view) {

        Intent intent = new Intent(this, BuySpotActivity.class);
        intent.putExtra("id", spotId);
        intent.putExtra(Constant.REQUEST_ID, requestId);
        intent.putExtra(Constant.OPEN_FOR_BOOK, false);
        intent.putExtra(Constant.PARTY_SIZE, partySize);
        intent.putExtra(Constant.SPOT_PRICE, spotPrice);
        intent.putExtra(Constant.BOOK_TIME, buildBookTime());
        startActivity(intent);

        finish();
    }

    private String buildBookTime() {

        return acceptRequestModel.getAcceptRequestData().getsTimeFrom() + " " +
                acceptRequestModel.getAcceptRequestData().getsAmPmFrom() + " - " +
                acceptRequestModel.getAcceptRequestData().getsTimeTo() + " " +
                acceptRequestModel.getAcceptRequestData().getsAmPmTo();
    }

    public void onClickCancel(View view) {

        finish();
    }
}

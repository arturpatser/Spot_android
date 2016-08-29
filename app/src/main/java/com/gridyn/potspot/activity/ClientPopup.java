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

public class ClientPopup extends AppCompatActivity {

    ActivityPopupBinding binding;
    String spotId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_popup);

        Bundle args = getIntent().getExtras();

        if (args != null) {

            binding.setMessage(args.getString(Constant.NOTIF_TEXT));
            binding.setSpotName(args.getString(Constant.SPOT_NAME));

            spotId = args.getString(Constant.SPOT_ID);
        }
    }

    public void onDetailsClick(View view) {

        Intent intent = new Intent(this, BuySpotActivity.class);
        intent.putExtra("id", spotId);
        startActivity(intent);
    }

    public void onClickCancel(View view) {

        finish();
    }
}

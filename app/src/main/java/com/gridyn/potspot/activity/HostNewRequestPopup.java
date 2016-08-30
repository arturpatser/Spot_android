package com.gridyn.potspot.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;

import com.gridyn.potspot.Constant;
import com.gridyn.potspot.Person;
import com.gridyn.potspot.R;
import com.gridyn.potspot.databinding.ActivityHostNewRequestPopupBinding;
import com.gridyn.potspot.response.SuccessResponse;
import com.gridyn.potspot.utils.ServerApiUtil;

import java.util.concurrent.TimeUnit;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class HostNewRequestPopup extends AppCompatActivity {

    ActivityHostNewRequestPopupBinding binding;
    private String dateTime;
    private String spotName;
    private String spotId;
    private String reqId;
    private String TAG = HostNewRequestPopup.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_host_new_request_popup);

        Bundle args = getIntent().getExtras();

        dateTime = args.getString(Constant.DATE_TIME_REQUEST);
        spotName = args.getString(Constant.SPOT_NAME);
        spotId = args.getString(Constant.SPOT_ID);
        reqId = args.getString(Constant.REQUEST_ID);

        binding.setSpotName(spotName);
        binding.setDateTime(dateTime);

        new CountDownTimer(300000, 1000) { // adjust the milli seconds here

            public void onTick(long millisUntilFinished) {
                binding.setTimerTime(""+String.format("%d min, %d sec",
                        TimeUnit.MILLISECONDS.toMinutes( millisUntilFinished),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
            }

            public void onFinish() {


            }
        }.start();

    }

    public void onClickCancel(View view) {

        Call<SuccessResponse> call = ServerApiUtil.initUser().declineBooking(reqId, Person.getTokenMap());

        call.enqueue(new Callback<SuccessResponse>() {
            @Override
            public void onResponse(Response<SuccessResponse> response, Retrofit retrofit) {

                SuccessResponse success = response.body();

                Log.d(TAG, "onResponse: resp = " + success);
                finish();
            }

            @Override
            public void onFailure(Throwable t) {

                Log.e(TAG, "onFailure: error while accept request = " + Log.getStackTraceString(t));
            }
        });
    }

    public void onDetailsClick(View view) {

        Call<SuccessResponse> call = ServerApiUtil.initUser().confirmBooking(reqId, Person.getTokenMap());

        call.enqueue(new Callback<SuccessResponse>() {
            @Override
            public void onResponse(Response<SuccessResponse> response, Retrofit retrofit) {

                SuccessResponse success = response.body();
                finish();

                Log.d(TAG, "onResponse: resp = " + success);
            }

            @Override
            public void onFailure(Throwable t) {

                Log.e(TAG, "onFailure: error while accept request = " + Log.getStackTraceString(t));
            }
        });
    }
}

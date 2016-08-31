package com.gridyn.potspot.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.gridyn.potspot.Constant;
import com.gridyn.potspot.Person;
import com.gridyn.potspot.R;
import com.gridyn.potspot.databinding.ActivityInvitedToJoinPopupBinding;
import com.gridyn.potspot.response.SuccessResponse;
import com.gridyn.potspot.utils.ServerApiUtil;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class InvitedToJoinPopup extends AppCompatActivity {

    ActivityInvitedToJoinPopupBinding binding;
    String spotId, whoInviteName, whoInviteId, spotName, requestId;
    private String TAG = InvitedToJoinPopup.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_invited_to_join_popup);

        Bundle args = getIntent().getExtras();

        if (args != null) {

            spotId = args.getString(Constant.ARG_SPOT_ID);
            whoInviteName = args.getString(Constant.ARG_WHO_INVITE);
            whoInviteId = args.getString(Constant.ARG_WHO_INVITE_ID);
            spotName = args.getString(Constant.SPOT_NAME);
            requestId = args.getString(Constant.REQUEST_ID);

            binding.setSpotName(spotName);
            binding.setUserName(whoInviteName);
        }
    }

    public void onClickNo(View view) {

        Call<SuccessResponse> call = ServerApiUtil.initUser().declineInvite(requestId, Person.getTokenMap());

        call.enqueue(new Callback<SuccessResponse>() {
            @Override
            public void onResponse(Response<SuccessResponse> response, Retrofit retrofit) {

                SuccessResponse successResponse = response.body();

                if (successResponse == null) {

                    Toast.makeText(InvitedToJoinPopup.this, R.string.error_connection, Toast.LENGTH_SHORT).show();
                } else {

                    if (successResponse.isSuccess()) {

                        Toast.makeText(InvitedToJoinPopup.this, R.string.successfull_decline, Toast.LENGTH_SHORT).show();

                        finish();
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {

                Toast.makeText(InvitedToJoinPopup.this, R.string.error_connection, Toast.LENGTH_SHORT).show();

                Log.e(TAG, "onFailure: error while decline invite = " + Log.getStackTraceString(t));
            }
        });
    }

    public void onClickYes(View view) {

        Call<SuccessResponse> call = ServerApiUtil.initUser().confirmInvite(requestId, Person.getTokenMap());

        call.enqueue(new Callback<SuccessResponse>() {
            @Override
            public void onResponse(Response<SuccessResponse> response, Retrofit retrofit) {

                SuccessResponse successResponse = response.body();

                if (successResponse == null) {

                    Toast.makeText(InvitedToJoinPopup.this, R.string.error_connection, Toast.LENGTH_SHORT).show();
                } else {

                    if (successResponse.isSuccess()) {

                        Toast.makeText(InvitedToJoinPopup.this, R.string.successfull_invite, Toast.LENGTH_SHORT).show();

                        finish();
                    } else {

                        Toast.makeText(InvitedToJoinPopup.this, R.string.credit_card_issue, Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {

                Toast.makeText(InvitedToJoinPopup.this, R.string.error_connection, Toast.LENGTH_SHORT).show();

                Log.e(TAG, "onFailure: error while accept invite = " + Log.getStackTraceString(t));
            }
        });
    }

    public void readMoreClick(View view) {
    }
}

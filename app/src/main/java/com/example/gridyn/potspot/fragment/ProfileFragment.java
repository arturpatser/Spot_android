package com.example.gridyn.potspot.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.gridyn.potspot.AssetsHelper;
import com.example.gridyn.potspot.Constant;
import com.example.gridyn.potspot.FastBlur;
import com.example.gridyn.potspot.Person;
import com.example.gridyn.potspot.R;
import com.example.gridyn.potspot.activity.ProfileEditActivity;
import com.example.gridyn.potspot.response.UserCommentsResponse;
import com.example.gridyn.potspot.response.UserInfoResponse;
import com.example.gridyn.potspot.service.UserService;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class ProfileFragment extends Fragment {

    private View mView;
    private boolean flag;
    private UserService mService;
    private static UserInfoResponse.Message.Data mMessage;

    private CircleImageView mAva;
    private TextView mBirthdate;
    private TextView mCardDescription;
    private TextView mCountReview;
    private TextView mAddress;
    private TextView mGender;
    private TextView mMemberSince;
    private TextView mName;
    private TextView mPayInfo;
    private TextView mAbout;
    private TextView mEmail;
    private TextView mPhone;
    private TextView mRealID;
    private LinearLayout mVerified;
    private Button mHostPanel;
    private CardView mListing;
    private TextView mSeeMore;

    public static ProfileFragment getInstance(UserInfoResponse.Message.Data messageUser) {
        Bundle args = new Bundle();
        ProfileFragment fragment = new ProfileFragment();
        fragment.setArguments(args);

        mMessage = messageUser;

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(!flag) {
            flag = true;
            mView = inflater.inflate(R.layout.fragment_profile, container, false);

            initFields();
            initToolbar();
            setFonts();
            initHeader();
            initRetrofit();
            setUserInfo();
            getComments();
            initCard();
        }
        return mView;
    }

    private void initToolbar() {
        final Toolbar toolbar = (Toolbar) mView.findViewById(R.id.profile_toolbar);
        toolbar.inflateMenu(R.menu.profile_edit);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                final Intent intent = new Intent(mView.getContext(), ProfileEditActivity.class);
                intent.putExtra("name", mMessage.name);
                intent.putExtra("address", mMessage.address);
                intent.putExtra("about", mMessage.about);
                intent.putExtra("gender", mMessage.gender);
                intent.putExtra("birthday", mMessage.birthday);
                intent.putExtra("email", mMessage.email);
                intent.putExtra("phone", mMessage.phone);
                intent.putExtra("realID", mMessage.realID);
                startActivity(intent);
                return true;
            }
        });
    }

    private void setFonts() {

    }

    private void initRetrofit() {
        final Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constant.BASE_URL)
                .build();

        mService = retrofit.create(UserService.class);
    }

    public void setUserInfo() {
        mName.setText(mMessage.name);

        if(!mMessage.address.isEmpty()) {
            mAddress.setText(mMessage.address);
        }

        if(!mMessage.about.isEmpty()){
            mAbout.setText(mMessage.about);
        }

        if(!mMessage.gender.isEmpty()) {
            mGender.setText(mMessage.gender);
        }

        if(!mMessage.birthday.isEmpty()) {
            mPayInfo.setText(mMessage.birthday);
        }

        if(!mMessage.email.isEmpty()) {
            mEmail.setText(mMessage.email);
        }

        if(!mMessage.phone.isEmpty()) {
            mPhone.setText(mMessage.phone);
        }

        if(!mMessage.realID.isEmpty()) {
            mRealID.setText(mMessage.realID);
        }

        if(!Person.isHost()) {
            mVerified.setVisibility(View.GONE);
            mHostPanel.setVisibility(View.GONE);
            mSeeMore.setVisibility(View.GONE);
        }
    }

    private void initFields() {
        mAva = (CircleImageView) mView.findViewById(R.id.profile_ava);
        mBirthdate = (TextView) mView.findViewById(R.id.profile_birthdate);
        mCardDescription = (TextView) mView.findViewById(R.id.profile_card_desc);
        mCountReview = (TextView) mView.findViewById(R.id.profile_count_reviews);
        mAddress = (TextView) mView.findViewById(R.id.profile_address);
        mGender = (TextView) mView.findViewById(R.id.profile_gender);
        mMemberSince = (TextView) mView.findViewById(R.id.profile_member_since);
        mName = (TextView) mView.findViewById(R.id.profile_name);
        mPayInfo = (TextView) mView.findViewById(R.id.profile_pay_info);
        mAbout = (TextView) mView.findViewById(R.id.profile_about);
        mEmail = (TextView) mView.findViewById(R.id.profile_email);
        mPhone = (TextView) mView.findViewById(R.id.profile_phone);
        mRealID = (TextView) mView.findViewById(R.id.profile_real_id);
        mSeeMore = (TextView) mView.findViewById(R.id.profile_see_more);
        mVerified = (LinearLayout) mView.findViewById(R.id.profile_verified);
        mHostPanel = (Button) mView.findViewById(R.id.profile_host_panel);
        mListing = (CardView) mView.findViewById(R.id.profile_card);
    }

    private void initCard() {
        mListing.setBackground(AssetsHelper.loadImageFromAsset(mView.getContext(), "images/chairs.jpg"));
    }


    public void getComments() {
        Call<UserCommentsResponse> call = mService.getComments(Person.getId());
        call.enqueue(new Callback<UserCommentsResponse>() {
            @Override
            public void onResponse(Response<UserCommentsResponse> response, Retrofit retrofit) {
                UserCommentsResponse res = response.body();
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    private void initHeader() {
        final FrameLayout header = (FrameLayout) mView.findViewById(R.id.profile_header);
        FastBlur.setBackgroundBlur(header, mView.getContext(), "images/chairs.jpg", mView.getResources());
    }
}

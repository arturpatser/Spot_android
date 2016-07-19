package com.gridyn.potspot.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
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

import com.gridyn.potspot.AssetsHelper;
import com.gridyn.potspot.FastBlur;
import com.gridyn.potspot.Person;
import com.gridyn.potspot.R;
import com.gridyn.potspot.activity.ProfileEditActivity;
import com.gridyn.potspot.response.UserInfoResponse;
import com.gridyn.potspot.service.UserService;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

import static com.gridyn.potspot.Constant.BASE_IMAGE;
import static com.gridyn.potspot.Constant.BASE_URL;
import static com.gridyn.potspot.Constant.CONNECTION_ERROR;
import static com.gridyn.potspot.Constant.NOT_SPECIFIED;
import static com.gridyn.potspot.Constant.URL_IMAGE;

public class ProfileFragment extends Fragment {

    private View mView;
    private boolean flag;
    private UserService mService;

    private CircleImageView mAvatar;
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
    private CardView mCard;
    private TextView mSeeMore;
    private TextView mTVListing;
    private int mCountComment = 0;

    public static ProfileFragment getInstance() {
        Bundle args = new Bundle();
        ProfileFragment fragment = new ProfileFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (!flag) {
            flag = true;
            mView = inflater.inflate(R.layout.fragment_profile, container, false);

            initFields();
            initToolbar();
            setFonts();
            initHeader();
            initRetrofit();
            goneView();
//            getComments();
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
                .baseUrl(BASE_URL)
                .build();

        mService = retrofit.create(UserService.class);
    }

    public void goneView() {
        if (!Person.isHost()) {
            mVerified.setVisibility(View.GONE);
            mHostPanel.setVisibility(View.GONE);
            mSeeMore.setVisibility(View.GONE);
            mCard.setVisibility(View.GONE);
            mTVListing.setVisibility(View.GONE);
        }

        if (mCountComment == 0) {
            final LinearLayout reviewView = (LinearLayout) mView.findViewById(R.id.profile_reviews_view);
            reviewView.setVisibility(View.GONE);
        }
    }

    private void initFields() {
        mAvatar = (CircleImageView) mView.findViewById(R.id.profile_ava);
        mBirthdate = (TextView) mView.findViewById(R.id.profile_birthdate);
        mCardDescription = (TextView) mView.findViewById(R.id.profile_card_name);
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
        mTVListing = (TextView) mView.findViewById(R.id.profile_tv_listing);
        mVerified = (LinearLayout) mView.findViewById(R.id.profile_verified);
        mHostPanel = (Button) mView.findViewById(R.id.profile_host_panel);
        mCard = (CardView) mView.findViewById(R.id.profile_card);
    }

    private void initCard() {
        mCard.setBackground(AssetsHelper.loadImageFromAsset(mView.getContext(), "images/chairs.jpg"));
    }


    public void getComments() {
      /*  Call<UserCommentsResponse> call = mService.getComments(Person.getId());
        call.enqueue(new Callback<UserCommentsResponse>() {
            @Override
            public void onResponse(Response<UserCommentsResponse> response, Retrofit retrofit) {
                UserCommentsResponse res = response.body();
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });*/
    }

    private void initHeader() {
        final FrameLayout header = (FrameLayout) mView.findViewById(R.id.profile_header);
        FastBlur.setBackgroundBlur(header, mView.getContext(), "images/chairs.jpg", mView.getResources());
    }

    @Override
    public void onStart() {
        super.onStart();
        Call<UserInfoResponse> call = mService.getUserInfo(Person.getId());
        call.enqueue(new Callback<UserInfoResponse>() {
            @Override
            public void onResponse(retrofit.Response<UserInfoResponse> response, Retrofit retrofit) {
                UserInfoResponse.Message message = response.body().message.get(0);
                mName.setText(message.data.name);
                mAddress.setText(message.data.address);
                if (!message.data.about.isEmpty()) {
                    mAbout.setText(message.data.about);
                } else {
                    mAbout.setText(NOT_SPECIFIED);
                }
                if (!message.data.gender.isEmpty()) {
                    mGender.setText(message.data.gender);
                } else {
                    mGender.setText(NOT_SPECIFIED);
                }
                if (!message.data.birthday.isEmpty()) {
                    mBirthdate.setText(message.data.birthday);
                } else {
                    mBirthdate.setText(NOT_SPECIFIED);
                }
                mEmail.setText(message.data.email);
                if (!message.data.about.isEmpty()) {
                    mPhone.setText(message.data.phone);
                } else {
                    mPhone.setText(NOT_SPECIFIED);
                }
                if (!message.data.paypalID.isEmpty() && !message.data.cardID.isEmpty()) {
                    mPayInfo.setText("Card and Paypal");
                } else if (!message.data.paypalID.isEmpty()) {
                    mPayInfo.setText("Paypal");
                } else if (!message.data.cardID.isEmpty()) {
                    mPayInfo.setText("Card");
                } else {
                    mPayInfo.setText(NOT_SPECIFIED);
                }

                mMemberSince.setText(new SimpleDateFormat("MMMM yyyy")
                        .format(new Date((long) message.system.timeCreated * 1000)));
                if (message.data.imgs.length != 0) {
                    if (!message.data.imgs[0].isEmpty()) {
                        Picasso.with(mView.getContext())
                                .load(URL_IMAGE + message.data.imgs[0])
                                .into(mAvatar);
                    }
                } else {
                    Picasso.with(getContext())
                            .load(URL_IMAGE + BASE_IMAGE)
                            .into(mAvatar);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Snackbar.make(getView(), CONNECTION_ERROR, Snackbar.LENGTH_SHORT).show();
            }
        });
    }
}

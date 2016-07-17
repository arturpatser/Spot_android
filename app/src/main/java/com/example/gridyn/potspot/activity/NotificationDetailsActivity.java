package com.example.gridyn.potspot.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.gridyn.potspot.AssetsHelper;
import com.example.gridyn.potspot.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class NotificationDetailsActivity extends AppCompatActivity {

    private CircleImageView mProfileImage;
    private TextView mProfileName;
    private TextView mProfileFrom;
    private TextView mPartySize;
    private TextView mDate;
    private Button mAccept;
    private Button mReject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_details);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        initFields();
        initHeader();
    }

    private void initFields() {
        mProfileImage = (CircleImageView) findViewById(R.id.sch_dtl_profile_image);
        mProfileName = (TextView) findViewById(R.id.sch_dtl_profile_name);
        mProfileFrom = (TextView) findViewById(R.id.sch_dtl_profile_from);
        mPartySize = (TextView) findViewById(R.id.sch_dtl_party_size);
        mDate = (TextView) findViewById(R.id.sch_dtl_date);
        mAccept = (Button) findViewById(R.id.sch_dtl_accept);
        mReject = (Button) findViewById(R.id.sch_dtl_reject);

    }

    private void initHeader() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.sch_dtl_toolbar);
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        final LinearLayout header = (LinearLayout) findViewById(R.id.sch_dtl_header);
        header.setBackground(AssetsHelper.loadImageFromAsset(getApplicationContext(), "images/mountain.jpg"));
    }

    public void onClickListingAccept(View view) {
        mAccept.setVisibility(View.INVISIBLE);
        mReject.setVisibility(View.INVISIBLE);
    }

    public void onCLickListingReject(View view) {
        mAccept.setVisibility(View.INVISIBLE);
        mReject.setVisibility(View.INVISIBLE);
    }
}

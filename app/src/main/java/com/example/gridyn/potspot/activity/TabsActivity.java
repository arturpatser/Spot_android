package com.example.gridyn.potspot.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.gridyn.potspot.Constant;
import com.example.gridyn.potspot.R;
import com.example.gridyn.potspot.adapter.TabsPagerFragmentAdapter;
import com.example.gridyn.potspot.response.UserInfoResponse;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class TabsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar mToolbar;
    private DrawerLayout mDrawer;
    private UserInfoResponse.Message.Data mMessageData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabs);
        initExtras();
        initToolbar();
        initTabs();
        initNav();
        initHeader();
    }

    private void initHeader() {
        final View viewHeader = getLayoutInflater()
                .inflate(R.layout.nav_header_main, (ViewGroup) findViewById(R.id.nav_header), false);
        final CircleImageView avatar = (CircleImageView) viewHeader.findViewById(R.id.nav_avatar);
        final TextView name = (TextView) viewHeader.findViewById(R.id.nav_name);
        final TextView email = (TextView) viewHeader.findViewById(R.id.nav_email);
        Picasso.with(getApplicationContext())
                .load(Constant.URL_IMAGE + mMessageData.imgs[0])
                .into(avatar);
        name.setText(mMessageData.name);
        email.setText(mMessageData.email);
    }

    private void initExtras() {
        final Bundle extra = getIntent().getExtras();
        mMessageData = new UserInfoResponse().new Message().new Data();
        mMessageData.name = extra.getString("name");
        mMessageData.address = extra.getString("address");
        mMessageData.about = extra.getString("about");
        mMessageData.gender = extra.getString("gender");
        mMessageData.birthday = extra.getString("birthday");
        mMessageData.email = extra.getString("email");
        mMessageData.phone = extra.getString("phone");
        mMessageData.realID = extra.getString("realID");
        mMessageData.imgs[0] = extra.getString("avatar");

    }

    private void initNav() {
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, mToolbar, R.string.nd_open, R.string.nd_close);
            mDrawer.setDrawerListener(toggle);
            toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void initToolbar() {
        final TextView titleToolbar = (TextView) findViewById(R.id.toolbar_title);
        mToolbar = (Toolbar) findViewById(R.id.toolbar_tabs);
        titleToolbar.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Roboto-Medium.ttf"));
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
    }

    private void initTabs() {
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        TabsPagerFragmentAdapter adapter = new TabsPagerFragmentAdapter(getSupportFragmentManager(), mMessageData);
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.home);
        tabLayout.getTabAt(1).setIcon(R.drawable.notification);
        tabLayout.getTabAt(2).setIcon(R.drawable.profile);
    }

    public void onClickDetails(View view) {
        Snackbar.make(view, "details", Snackbar.LENGTH_SHORT).show();
    }

    public void onClickAccept(View view) {
        Snackbar.make(view, "accept", Snackbar.LENGTH_SHORT).show();
    }

    public void onClickReject(View view) {
        Snackbar.make(view, "reject", Snackbar.LENGTH_SHORT).show();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
    Intent intent = null;

        switch (item.getItemId()) {
            case R.id.nav_search:
                intent = new Intent(this, SearchCriteriaActivity.class);
                break;
            case R.id.nav_message:
                intent = new Intent(this, MessageActivity.class);
                break;
            case R.id.nav_pay_spot:
                intent = new Intent(this, PaidSpotsActivity.class);
                break;
            case R.id.nav_my_spot:
                intent = new Intent(this, MySpotsActivity.class);
                break;
            case R.id.nav_friends:
                intent = new Intent(this, InviteFriendActivity.class);
                break;
            case R.id.nav_feedback:
                intent = new Intent(this, FeedbackActivity.class);
                break;
        }

        if(intent!=null) {
            startActivity(intent);
        }

        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawer.closeDrawer(GravityCompat.START);

        return true;
    }
}

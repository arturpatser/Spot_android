package com.gridyn.potspot.activity;

import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.gridyn.potspot.Constant;
import com.gridyn.potspot.Person;
import com.gridyn.potspot.R;
import com.gridyn.potspot.SelectPageUtil;
import com.gridyn.potspot.adapter.TabsPagerFragmentAdapter;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class TabsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = TabActivity.class.getName();
    private Toolbar mToolbar;
    private DrawerLayout mDrawer;
    private View mHeaderView;
    private TextView mTitleToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabs);
        initToolbar();
        initTabs();
        initNav();
        initHeader();
    }

    private void initHeader() {
        final Bundle extra = getIntent().getExtras();
        final CircleImageView avatar = (CircleImageView) mHeaderView.findViewById(R.id.nav_avatar);
        final TextView name = (TextView) mHeaderView.findViewById(R.id.nav_name);
        final TextView email = (TextView) mHeaderView.findViewById(R.id.nav_email);
        Picasso.with(getApplicationContext())
                .load(Constant.URL_IMAGE + extra.getString("avatar"))
                .into(avatar);
        name.setText(extra.getString("name"));
        email.setText(extra.getString("email"));
    }

    private void initNav() {
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, mToolbar, R.string.nd_open, R.string.nd_close);
        mDrawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        mHeaderView = navigationView.inflateHeaderView(R.layout.nav_header_main);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void initToolbar() {
        mTitleToolbar = (TextView) findViewById(R.id.toolbar_title);
        mTitleToolbar.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Roboto-Medium.ttf"));
        mToolbar = (Toolbar) findViewById(R.id.toolbar_tabs);
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
    }

    private void initTabs() {
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        SelectPageUtil.init(tabLayout, mTitleToolbar);
        TabsPagerFragmentAdapter adapter = new TabsPagerFragmentAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);
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

            case R.id.nav_become_host:

                Log.d(TAG, "onOptionsItemSelected: add potspot clicked");

                if (!Person.isHost()) {
                    Snackbar.make(mDrawer, "Your account is not verified", Snackbar.LENGTH_INDEFINITE)
                            .setAction("goto verify", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    final Intent intent = new Intent(TabsActivity.this, VerificationActivity.class);
                                    startActivity(intent);
                                }
                            })
                            .setActionTextColor(getResources().getColor(R.color.mainRed))
                            .show();
                } else if (Person.isHost()) {
                    intent = new Intent(TabsActivity.this, SpaceActivity.class);
                }

                    break;

            case R.id.nav_search:
                intent = new Intent(this, SearchCriteriaActivity.class);
                break;
            case R.id.nav_message:
                intent = new Intent(this, MessageActivity.class);
                break;
            case R.id.nav_pay_spot:
                intent = new Intent(this, PaidSpotsActivity.class);
                break;
            case R.id.nav_favourites:

                break;
            case R.id.nav_my_spot:
                intent = new Intent(this, MySpotsActivity.class);
                break;
            case R.id.nav_my_money:
                intent = new Intent(this, MyMoneyActivity.class);
                break;
            case R.id.nav_friends:
                intent = new Intent(this, InviteFriendActivity.class);
                break;
            case R.id.nav_feedback:
                intent = new Intent(this, FeedbackActivity.class);
                break;
            case R.id.nav_log_out:
                intent = new Intent(this, MainActivity.class);
                SharedPreferences settings = getSharedPreferences(Constant.APP_PREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = settings.edit();
                editor.putBoolean(Constant.AP_LOG_IN, false);
                editor.apply();
                startActivity(intent);
                finish();
                return true;
        }

        if (intent != null) {
            startActivity(intent);
        }

        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawer.closeDrawer(GravityCompat.START);

        return true;
    }
}

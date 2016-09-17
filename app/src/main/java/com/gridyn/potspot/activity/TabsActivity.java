package com.gridyn.potspot.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
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

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.gridyn.potspot.Constant;
import com.gridyn.potspot.Person;
import com.gridyn.potspot.R;
import com.gridyn.potspot.SelectPageUtil;
import com.gridyn.potspot.adapter.TabsPagerFragmentAdapter;
import com.gridyn.potspot.utils.SharedPrefsUtils;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class TabsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = TabsActivity.class.getName();
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

        String mes = getIntent().getExtras().getString(Constant.PROGRESS_MESSAGE);

        if (mes != null)
            Snackbar.make(findViewById(android.R.id.content), mes, Snackbar.LENGTH_SHORT).show();
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
        final ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        SelectPageUtil.init(tabLayout, mTitleToolbar);
        final TabsPagerFragmentAdapter adapter = new TabsPagerFragmentAdapter(getSupportFragmentManager(), this);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(0);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                switch (position) {

                    case 0:
                        mTitleToolbar.setText(getString(R.string.home));
                        break;
                    case 1:
                        mTitleToolbar.setText(getString(R.string.notification));
                        break;
                    case 2:
                        mTitleToolbar.setText(getString(R.string.profile));
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
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
                    Snackbar.make(mDrawer, "Your account is not verified", Snackbar.LENGTH_SHORT)
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
                intent = new Intent(this, FavoritesActivity.class);
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

                SharedPrefsUtils.setBooleanPreference(this, Constant.FB_APP_LOGIN, false);
                SharedPrefsUtils.setBooleanPreference(this, Constant.GPLUS_APP_LOGIN, false);

                startActivity(intent);

                GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestEmail()
                        .requestProfile()
                        .requestIdToken("361315588006-i4lal5mo7os4urvp20lo8gmbt5o4jml1.apps.googleusercontent.com")
                        .build();

                GoogleApiClient mGoogleApiClient = new GoogleApiClient.Builder(this)
                        .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                        .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                        .build();

                mGoogleApiClient.connect();

                if (mGoogleApiClient.isConnected()) {

                    Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                            new ResultCallback<Status>() {
                                @Override
                                public void onResult(Status status) {

                                }
                            });
                }

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

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}

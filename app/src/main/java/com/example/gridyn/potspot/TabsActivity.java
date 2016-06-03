package com.example.gridyn.potspot;

import android.app.SearchManager;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.gridyn.potspot.adapter.TabsPagerFragmentAdapter;

public class TabsActivity extends AppCompatActivity {

    private Integer[] tabs = {
            R.drawable.home,
            R.drawable.notification,
            R.drawable.profile
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabs);
        initToolbar();
        initTabs();
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        final TextView titleToolbar = (TextView) findViewById(R.id.toolbar_title);
        if (titleToolbar != null) {
            titleToolbar.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Roboto-Medium.ttf"));
        }
        if(toolbar!=null) {
            toolbar.inflateMenu(R.menu.main_menu);
        }
    }

    private void initTabs() {
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        TabsPagerFragmentAdapter adapter = new TabsPagerFragmentAdapter(getSupportFragmentManager(), getApplicationContext());
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

/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);


        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        MenuItem searchMenuItem = menu.findItem(R.id.menu_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchMenuItem);
        if(searchView!=null)
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        return true;
    }*/
}

package com.gridyn.potspot.adapter;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.widget.TextView;

import com.gridyn.potspot.fragment.HomeFragment;
import com.gridyn.potspot.fragment.NotificationFragment;
import com.gridyn.potspot.fragment.ProfileFragment;

import java.util.Arrays;
import java.util.List;

public class TabsPagerFragmentAdapter extends FragmentPagerAdapter {

    private final Context context;
    private List<Fragment> mFragmentList;
    private TabLayout mTabLayout;
    private TextView mTitleToolbar;

    public TabsPagerFragmentAdapter(FragmentManager fm, Context context) {
        super(fm);
        mFragmentList = Arrays.asList(
                HomeFragment.getInstance(),
                NotificationFragment.getInstance(),
                ProfileFragment.getInstance()
        );

        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {

        return "";
    }
}

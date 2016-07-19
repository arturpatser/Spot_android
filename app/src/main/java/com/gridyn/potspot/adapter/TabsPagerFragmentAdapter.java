package com.gridyn.potspot.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.gridyn.potspot.fragment.HomeFragment;
import com.gridyn.potspot.fragment.NotificationFragment;
import com.gridyn.potspot.fragment.ProfileFragment;

import java.util.Arrays;
import java.util.List;

public class TabsPagerFragmentAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragmentList;

    public TabsPagerFragmentAdapter(FragmentManager fm) {
        super(fm);

        mFragmentList = Arrays.asList(
                HomeFragment.getInstance(),
                NotificationFragment.getInstance(),
                ProfileFragment.getInstance()
        );
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
        return null;
    }
}

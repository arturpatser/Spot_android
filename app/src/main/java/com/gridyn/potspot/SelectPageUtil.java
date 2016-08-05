package com.gridyn.potspot;

import android.support.design.widget.TabLayout;
import android.widget.TextView;

public abstract class SelectPageUtil {

    private static TextView mTitle;
    private static TabLayout mTabLayout;

    public static void init(TabLayout tabLayout, TextView title) {
        mTabLayout = tabLayout;
        mTitle = title;
    }

    public static void selectHome() {
        if (mTabLayout.getSelectedTabPosition() == 0) {
            mTitle.setText(R.string.home);
            mTabLayout.getTabAt(0).setIcon(R.drawable.home);
            mTabLayout.getTabAt(1).setIcon(R.drawable.notification);
            mTabLayout.getTabAt(2).setIcon(R.drawable.profile);
        }
    }

    public static void selectNotification() {
        if (mTabLayout.getSelectedTabPosition() == 1) {
            mTitle.setText(R.string.notification);
            mTabLayout.getTabAt(0).setIcon(R.drawable.home);
            mTabLayout.getTabAt(1).setIcon(R.drawable.notification);
            mTabLayout.getTabAt(2).setIcon(R.drawable.profile);
        }
    }

    public static void selectProfile() {
        if (mTabLayout.getSelectedTabPosition() == 2) {
            mTitle.setText(R.string.profile);
            mTabLayout.getTabAt(0).setIcon(R.drawable.home);
            mTabLayout.getTabAt(1).setIcon(R.drawable.notification);
            mTabLayout.getTabAt(2).setIcon(R.drawable.guests);
        }
    }
}

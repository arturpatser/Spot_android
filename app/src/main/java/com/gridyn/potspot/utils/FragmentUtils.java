package com.gridyn.potspot.utils;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;


/**
 * Created by dmytro_vodnik on 11.06.2015.
 */
public class FragmentUtils {

    private static final String TAG = "FRAGMENT UTILS";

    /**
     * Method allows you to open any fragment with easy call FragmentUtils.openFragment(...) in every
     * place of your app
     * @param which - fragment to open
     * @param where - Layout where we need to put fragment
     * @param tag - tag for this fragment
     * @param context - God object
     * @param isAddToBackStack - flag add to backstack or not
     */
    public static void openFragment(Fragment which, int where, String tag, Context context, Boolean isAddToBackStack){

        FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();

        if (fragmentManager.findFragmentByTag(tag) == null) {

            FragmentTransaction t = fragmentManager.beginTransaction();

            t.add(where, which, tag);
            if (isAddToBackStack)
                t.addToBackStack(tag);
            t.commit();
        }
    }

    /**
     * Close any fragment by its tag, just call FragmentUtils.closeFragment(...)
     * @param context - God object
     * @param tag - tag of fragment which we need to close
     */
    public static void closeFragment(Context context, String tag) {

        FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();

        Fragment fragment = fragmentManager.findFragmentByTag(tag);

        if(fragment != null) {
            fragmentManager.beginTransaction()
                    .detach(fragment)
                    .remove(fragment)
                    .commit();
            popBackStack(context);
        }
    }

    /**
     * Easy pop backstack
     * @param context - God object
     */
    public static void popBackStack(Context context) {

        ((FragmentActivity) context).getSupportFragmentManager().popBackStack();
    }

    /**
     * Clears all backstack from fragments
     * @param context
     */
    public static void clearBackStack(Context context) {

        FragmentManager manager = ((FragmentActivity) context).getSupportFragmentManager();
        if (manager.getBackStackEntryCount() > 0) {
            FragmentManager.BackStackEntry first = manager.getBackStackEntryAt(0);
            manager.popBackStack(first.getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }
}

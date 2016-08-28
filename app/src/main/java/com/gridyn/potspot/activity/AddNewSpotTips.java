package com.gridyn.potspot.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.gridyn.potspot.Constant;
import com.gridyn.potspot.R;
import com.gridyn.potspot.fragment.OneTimeFragment;
import com.matthewtamlin.sliding_intro_screen_library.buttons.IntroButton;
import com.matthewtamlin.sliding_intro_screen_library.core.IntroActivity;
import com.matthewtamlin.sliding_intro_screen_library.core.LockableViewPager;
import com.matthewtamlin.sliding_intro_screen_library.transformers.MultiViewParallaxTransformer;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by dmytro_vodnik on 8/25/16.
 * working on potspot project
 */
public class AddNewSpotTips extends IntroActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.NoActionBar); // Looks good when the status bar is hidden
        super.onCreate(savedInstanceState);

        // Skip to the next Activity if the user has previously completed the introduction
        if (introductionCompletedPreviously()) {
            final Intent nextActivity = new Intent(this, MySpotsActivity.class);
            startActivity(nextActivity);
            finish();
        }

        configureTransformer();

        LockableViewPager viewPager = (LockableViewPager) findViewById(com.matthewtamlin.sliding_intro_screen_library.R.id.intro_activity_viewPager);
        viewPager.setOffscreenPageLimit(0);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                getPage(position).onResume();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * Called by {@link #onCreate(Bundle)} to generate the pages displayed in this activity. The
     * returned Collection is copied, so further changes to the collection will have no effect after
     * this method returns. The total ordering of the returned collection is maintained in the
     * display of the pages.
     *
     * @param savedInstanceState
     * 		if this activity is being re-initialized after previously being shut down, then this Bundle
     * 		contains the data this activity most recently saved in {@link
     * 		#onSaveInstanceState(Bundle)}, otherwise null
     * @return the pages to display in the Activity, not null
     */
    @Override
    protected Collection<Fragment> generatePages(Bundle savedInstanceState) {
        // This variable holds the pages while they are being created
        final ArrayList<Fragment> pages = new ArrayList<>();

        pages.add(OneTimeFragment.newInstance(null, R.drawable.s1));
        pages.add(OneTimeFragment.newInstance(null, R.drawable.s2));
        pages.add(OneTimeFragment.newInstance(null, R.drawable.s3));
        pages.add(OneTimeFragment.newInstance(null, R.drawable.s4));
        pages.add(OneTimeFragment.newInstance(null, R.drawable.s5));
        pages.add(OneTimeFragment.newInstance(null, R.drawable.s6));

        return pages;
    }

    /**
     * Called by {@link #onCreate(Bundle)} to generate the Behaviour of the final button. The {@link
     * IntroButton} class contains Behaviours which suit most needs. The Behaviour of the final
     * button can be changed later using {@link #getFinalButtonAccessor()}.
     *
     * @return the Behaviour to use for the final button, not null
     */
    @Override
    protected IntroButton.Behaviour generateFinalButtonBehaviour() {
		/* The pending changes to the shared preferences editor will be applied when the
		 * introduction is successfully completed. By setting a flag in the pending edits and
		 * checking the status of the flag when the activity starts, the introduction screen can
		 * be skipped if it has previously been completed.
		 */
        final SharedPreferences sp = getSharedPreferences("ME_PREF", Context.MODE_PRIVATE);
        final SharedPreferences.Editor pendingEdits = sp.edit().putBoolean(Constant.ADD_SPOT_ONE_TIME, true);

        // Define the next activity intent and create the Behaviour to use for the final button
        final Intent nextActivity = new Intent(this, MySpotsActivity.class);
        return new IntroButton.ProgressToNextActivity(nextActivity, pendingEdits);
    }

    /**
     * Checks for a shared preference flag indicating that the introduction has been completed
     * previously.
     *
     * @return true if the introduction has been completed before, false otherwise
     */
    private boolean introductionCompletedPreviously() {
        final SharedPreferences sp = getSharedPreferences("ME_PREF", Context.MODE_PRIVATE);
        return sp.getBoolean(Constant.ADD_SPOT_ONE_TIME, false);
    }

    /**
     * Sets this IntroActivity to use a MultiViewParallaxTransformer page transformer.
     */
    private void configureTransformer() {
        final MultiViewParallaxTransformer transformer = new MultiViewParallaxTransformer();
        transformer.withParallaxView(R.id.page_fragment_imageHolderFront, 1.2f);
        setPageTransformer(false, transformer);
    }
}

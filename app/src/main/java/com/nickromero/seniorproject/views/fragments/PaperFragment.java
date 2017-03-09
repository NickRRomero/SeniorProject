package com.nickromero.seniorproject.views.fragments;

import android.content.Context;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by nickromero on 12/23/16.
 */

/**
 * PaperFragment contains the sub Paper Fragments within the applications.
 */
public class PaperFragment extends FragmentPagerAdapter {

    /**
     * Number of sub-fragments
     */
    final private int PAGE_COUNT = 3;

    /**
     * Title of each page
     */
    final private String mTabTitles[] = new String[] {"Saved", "Subscriptions",
        "Suggested"};


    /**
     * Saved Fragment
     */
    public SavedPaperFragment mSavedFragment;

    /**
     * Subscribed Fragment
     */
    public SubscribedPaperFragment mSubscribedFragment;

    /**
     * Suggested Fragment
     */
    public SuggestedPaperFragment mSuggestedFragment;

    /**
     * Constructor for a PaperFragment
     * @param fm Fragment manger
     * @param context
     */
    public PaperFragment(android.support.v4.app.FragmentManager fm, Context context) {
        super(fm);

        mSavedFragment = new SavedPaperFragment();
        mSubscribedFragment = new SubscribedPaperFragment();
        mSuggestedFragment = new SuggestedPaperFragment();

    }

    /**
     * Handle switching between fragments and which should be shown to the user
     * @param position current displayed fragment
     * @return Fragment
     */
    @Override
    public android.support.v4.app.Fragment getItem(int position) {
        if (position == 0)
            return mSavedFragment;
        else if (position == 1)
            return mSubscribedFragment;
        else
            return mSuggestedFragment;
    }


    /**
     * Get the number of fragments/pages
     * @return number of fragments/pages
     */
    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    /**
     * Get the title of the fragment
     * @param position currently viewed fragment
     * @return the title
     */
    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return mTabTitles[position];
    }
}

package com.nickromero.seniorproject.views.fragments;

import android.content.Context;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by nickromero on 12/23/16.
 */

public class PaperFragment extends FragmentPagerAdapter {
    final private int PAGE_COUNT = 3;

    final private String mTabTitles[] = new String[] {"Saved", "Subscriptions",
        "Suggested"};

    final private Context mContext;

    public SavedPaperFragment mSavedFragment;
    public SubscribedPaperFragment mSubscribedFragment;
    public SuggestedPaperFragment mSuggestedFragment;


    public PaperFragment(android.support.v4.app.FragmentManager fm, Context context) {
        super(fm);
        this.mContext = context;

        mSavedFragment = new SavedPaperFragment();
        mSubscribedFragment = new SubscribedPaperFragment();
        mSuggestedFragment = new SuggestedPaperFragment();

    }

    @Override
    public android.support.v4.app.Fragment getItem(int position) {
        if (position == 0)
            return mSavedFragment;
        else if (position == 1)
            return mSubscribedFragment;
        else
            return mSuggestedFragment;
    }


    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return mTabTitles[position];
    }
}

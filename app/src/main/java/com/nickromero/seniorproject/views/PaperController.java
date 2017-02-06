package com.nickromero.seniorproject.views;

import android.support.v4.app.Fragment;

import com.nickromero.seniorproject.views.adapters.PaperAdapter;
import com.nickromero.seniorproject.views.adapters.SuggestedPaperAdapter;

import data.Paper;

/**
 * Created by nickromero on 2/5/17.
 */

public class PaperController {

    static PaperAdapter mSavedAdapter;
    static PaperAdapter mSubscribedAdapter;
    static SuggestedPaperAdapter mSuggestedAdapter;

    static Fragment mSavedFragment;
    static Fragment mSubscribedFragment;
    static Fragment mSuggestedFragment;

    private static PaperController mInstance;

    private PaperController(){};


    public static PaperController getInstance() {

        if (mInstance == null) {
            mInstance = new PaperController();
            return mInstance;
        }
        else
            return mInstance;
    }


    public void setAdapter(PaperAdapter adapter, PaperType which) {

        switch (which) {
            case SAVED:
                mSavedAdapter = adapter;
                break;
            case SUBSCRIBED:
                mSubscribedAdapter = adapter;
                break;
        }
    }

    public void setAdapter(SuggestedPaperAdapter adapter) {
        mSuggestedAdapter = adapter;
    }

    public void setFragment(Fragment fragment, PaperType which) {

        switch (which) {
            case SAVED:
                mSavedFragment = fragment;
                break;
            case SUBSCRIBED:
                mSubscribedFragment = fragment;
                break;
            case SUGGESTED:
                mSuggestedFragment =  fragment;
                break;
        }
    }

    public void attachAdapterToView(PaperType which) {

        switch (which) {
            case SAVED:
                ((SavedPaperFragment)mSavedFragment).attachAdapter(mSavedAdapter);
                break;
            case SUBSCRIBED:
                ((SubscribedPaperFragment)mSubscribedFragment).attachAdapter(mSubscribedAdapter);
                break;
        }

    }

    public void removeFromAdapter(int position, PaperType which) {
        switch (which) {
            case SAVED:
                mSavedAdapter.removeItem(position);
                break;
            case SUBSCRIBED:
                mSubscribedAdapter.removeItem(position);
                break;
        }
    }

    public void addToSavedAdapter(int position, PaperType which) {

        //System.out.println(position);
        System.out.println(mSubscribedAdapter.getItem(position));

        mSavedAdapter.addItem(mSubscribedAdapter.getItem(position));
        removeFromAdapter(position, which);
        //mSavedAdapter.notifyDataSetChanged();
        //mSubscribedAdapter.notifyDataSetChanged();
    }
}

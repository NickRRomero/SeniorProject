package com.nickromero.seniorproject.views.fragments;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;

import com.nickromero.seniorproject.views.MainActivity;
import com.nickromero.seniorproject.views.adapters.PaperAdapter;
import com.nickromero.seniorproject.views.adapters.SuggestedPaperAdapter;

import java.util.ArrayList;
import java.util.List;

import data.SQLConverter.SQLPaperConverter;
import data.enums.PaperType;
import data.models.Paper;
import data.models.XMLRoot;

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

    private PaperController() {
    }

    ;

    private Context mContext;

    private static SQLPaperConverter mSQLPaperConverter;


    public static PaperController getInstance(Context context) {

        if (mInstance == null) {
            mInstance = new PaperController();
            mSQLPaperConverter = SQLPaperConverter.getInstance(context);
            return mInstance;
        } else
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

    public void loadPapersFromSQLDatabase() {
            ArrayList<Paper> papers = mSQLPaperConverter.getPapersFromDatabase();
            for (Paper paper : papers) {
                if (paper.getType().equals(PaperType.SAVED.getType()))
                    mSavedAdapter.addItem(paper);
                else
                    mSubscribedAdapter.addItem(paper);
            }
    }

    public void setSuggestedPaperAdapter(SuggestedPaperAdapter adapter) {
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
                mSuggestedFragment = fragment;
                break;
        }
    }

    public void addToSavedAdapter(int position, PaperType which) {

        Paper paperToMove = mSubscribedAdapter.getItem(position);
        paperToMove.setType(PaperType.SAVED.getType());
        mSQLPaperConverter.adjustPaperType(paperToMove);

        mSavedAdapter.addItem(paperToMove);

        mSubscribedAdapter.removeItem(position);

    }

    public void removeFromAdapter(int position, PaperType which) {
        switch (which) {
            case SAVED:
                mSQLPaperConverter.deletePaperFromDatabase(mSavedAdapter.getItem(position));
                mSavedAdapter.removeItem(position);

                break;
            case SUBSCRIBED:
                mSQLPaperConverter.deletePaperFromDatabase(mSubscribedAdapter.getItem(position));
                mSubscribedAdapter.removeItem(position);

                break;
        }

    }



    public void addToSubscribedAdapter(List<Paper> papers) {
        mSubscribedAdapter.addItems(papers);
    }

    public void updateSubscribed(XMLRoot xmlRoot) {


        if (xmlRoot.totalfound > 0) {
            int i = 0;
            for (Paper paper : xmlRoot.getFoundPapers()) {
                if (i++ == 10)
                    break;
                paper.setType("Subscribed");
                mSubscribedAdapter.addItem(paper);
                mSQLPaperConverter.addPaperToDatabase(paper);
            }
        }
    }

    public void updateSuggested(XMLRoot xmlRoot) {
        if (xmlRoot.totalfound > 0) {
            int i = 0;
            for (Paper paper : xmlRoot.getFoundPapers()) {
                if (i++ == 10)
                    break;
                mSuggestedAdapter.addItem(paper);
            }
        }
    }


}

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
import data.SQLConverter.SQLPaperQualiferConverter;
import data.enums.PaperType;
import data.models.Paper;
import data.models.Qualifier;
import data.models.XMLRoot;

/**
 * Created by nickromero on 2/5/17.
 * A PaperController handles object moving across/between the fragments within the app.
 * At times papers are moved/deleted/copied from one tab to another.
 */
public class PaperController {

    /**
     * Adapter used by the SavedFragment Class
     */
    static PaperAdapter mSavedAdapter;

    /**
     * Adapter used by the SubscribedFragment Class
     */
    static PaperAdapter mSubscribedAdapter;

    /**
     * Adapter used by the SuggestedFragment Class
     */
    static SuggestedPaperAdapter mSuggestedAdapter;

    /**
     * Saved Fragment
     */
    private static Fragment mSavedFragment;

    /**
     * SubscribedFragment
     */
    private static Fragment mSubscribedFragment;

    /**
     * SuggestedFragment
     */
    private static Fragment mSuggestedFragment;

    private static PaperController mInstance;

    /**
     * Empty constructor
     */
    public PaperController() {}

    /**
     * Converter class used to handle SQLLite DB Calls for a paper
     */
    private static SQLPaperConverter mSQLPaperConverter;

    /**
     * Converter class used to handle SQLLite DB calls for a qualifier
     */
    private static SQLPaperQualiferConverter mSQLPaperQualiferConverter;


    /**
     * Return a new instance of this class
     * @param context calling context
     * @return PaperController instance
     */
    public PaperController getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new PaperController();
            mSQLPaperConverter = SQLPaperConverter.getInstance(context);
            mSQLPaperQualiferConverter = SQLPaperQualiferConverter.getInstance(context);
            return mInstance;
        } else
            return mInstance;
    }


    /**
     * After creating our PaperController we need to set up the proper adapters
     * @param adapter new adapter to use
     * @param which The type of adapter we are using
     */
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

    /**
     * SQLLite call to load all papers from the saved database
     */
    public void loadPapersFromSQLDatabase() {
            ArrayList<Paper> papers = mSQLPaperConverter.getPapersFromDatabase();

            for (Paper paper : papers) {
                paper.setQualifiers(mSQLPaperQualiferConverter.getPapersQualifiers(paper));

                //Properly place a paper in the correct adapter
                if (paper.getType().equals(PaperType.SAVED.getType()))
                    mSavedAdapter.addItem(paper);
                else
                    mSubscribedAdapter.addItem(paper);
            }
    }

    /**
     * Setup the suggested adapter.
     * @param adapter the new adapter
     */
    public void setSuggestedPaperAdapter(SuggestedPaperAdapter adapter) {
        mSuggestedAdapter = adapter;
    }

    /**
     * Set up the correct fragments for the controller.
     * @param fragment the fragment to set up
     * @param which Which fragment we are setting up.
     */
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

    /**
     * Add or move a paper to the Saved Adapter / Fragment
     * @param position of the paper within the SubscribedAdapter
     */
    void addToSavedAdapter(int position) {

        Paper paperToMove = mSubscribedAdapter.getItem(position);
        paperToMove.setType(PaperType.SAVED.getType());
        mSQLPaperConverter.adjustPaperType(paperToMove);

        mSavedAdapter.addItem(paperToMove);

        mSubscribedAdapter.removeItem(position);

    }

    /**
     * Remove a paper from an adapter. Also remove the paper from the local database
     * @param position of the paper to remove
     * @param which which adapter we are removing a paper from
     */
    void removeFromAdapter(int position, PaperType which) {
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

    /**
     * Add a collection of new papers to the SubscribedAdapter. This is used after receiving a new
     * list of papers from the qualifier check
     * @param papers collection of papers
     */
    void addToSubscribedAdapter(List<Paper> papers) {
        mSubscribedAdapter.addItems(papers);
    }

    /**
     * Update the subscribed adapter/fragment with new papers. These papers are recieved from a
     * Retrofit call after creating a new qualifier.
     * @param xmlRoot the root of the xml page queried
     * @param newQualifier The qualifer that was used to build this query
     */
    public void updateSubscribed(XMLRoot xmlRoot, Qualifier newQualifier) {

        //Check for any papers
        if (xmlRoot.totalfound > 0) {
            int i = 0;
            List<Paper> smallList = xmlRoot.getFoundPapers().subList(0, 5);

            for (Paper paper : smallList) {
                paper.setType("Subscribed");
                mSQLPaperConverter.addPaperToDatabase(paper);
            }

            //For now we just take the first 5 papers of a qualifer
            smallList = mSQLPaperConverter.getPapersFromDatabase();
            smallList = smallList.subList(smallList.size() - 5, smallList.size());

            for (Paper paper : smallList) {
                mSQLPaperQualiferConverter.setQualifiersForPaper(paper, newQualifier);
            }

            mSubscribedAdapter.removeAllItems();
            mSavedAdapter.removeAllItems();
            loadPapersFromSQLDatabase();
        }
    }

    /**
     * Update the saved adapter with a new paper.
     * @param xmlRoot the root of the xml page queried
     */
    public void updateSaved(XMLRoot xmlRoot) {
        if (xmlRoot.totalfound > 0) {
            for (Paper paper : xmlRoot.getFoundPapers()) {
                paper.setType("Saved");
                mSavedAdapter.addItem(paper);
                mSQLPaperConverter.addPaperToDatabase(paper);
            }
        }
    }

    /**
     * Update the saved adapter with a new paper.
     * @param xmlRoot the root of the xml page queried
     */
    void updateSuggested(XMLRoot xmlRoot) {
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

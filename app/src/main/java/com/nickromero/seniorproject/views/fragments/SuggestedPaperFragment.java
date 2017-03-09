package com.nickromero.seniorproject.views.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.nickromero.seniorproject.R;
import com.nickromero.seniorproject.views.adapters.PaperAdapter;

import data.providers.PaperProvider;
import io.reactivex.android.schedulers.AndroidSchedulers;


/**
 * Created by nickromero on 12/23/16.
 */

/**
 * A SuggestedPaperFragment handles display an endless list of papers searched for by the user.
 * The class is a listview that constantly updates itself with new papers as the user scrolls.
 */
public class SuggestedPaperFragment extends Fragment {

    public static final int ITEMS_COUNT = 15;
    /**
     * Current number of papers
     */
    private int paperCount = 0;

    /**
     * Scroll listener to display an endless amount of queried papers
     */
    private EndlessRecyclerViewScrollListener scrollListener;

    /**
     * View to hold the papers
     */
    private RecyclerView mRecyclerView;

    /**
     * Search bar for the user to enter a search term
     */
    private EditText searchBarText;

    /**
     * Controller to be used to save papers from the suggested fragment
     */
    private static PaperController mPaperController;

    /**
     * Current searched term
     */
    private String currentSearchTerm;

    /**
     * Super class call to create a view for this paper
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.suggested_fragment_view, container, false);

        mPaperController = new PaperController().getInstance(getContext());

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerViewSuggestedFragment);

        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());

        searchBarText = (EditText) rootView.findViewById(R.id.searchEditText);

        ImageView searchBarIcon = (ImageView) rootView.findViewById(R.id.searchBarIcon);
        searchBarIcon.setOnClickListener(v -> {
            currentSearchTerm = searchBarText.getText().toString();
            if (!currentSearchTerm.isEmpty()) {
                scrollListener.onLoadMore(0, ITEMS_COUNT, mRecyclerView);
            }
        });

        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        //Handles the endless scrolling and updating of papers
        scrollListener = new EndlessRecyclerViewScrollListener(mLinearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                paperCount += 15;
                PaperProvider.getPapersByMetadata(currentSearchTerm, paperCount)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(mPaperController::updateSuggested,
                                throwable -> System.out.println(throwable.toString())
                                , () -> System.out.println("Successful")

                        );
            }
        };

        mRecyclerView.addOnScrollListener(scrollListener);

        mRecyclerView.setAdapter(PaperController.mSuggestedAdapter);

        return rootView;
    }


}

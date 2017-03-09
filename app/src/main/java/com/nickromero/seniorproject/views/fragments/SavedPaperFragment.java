package com.nickromero.seniorproject.views.fragments;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nickromero.seniorproject.R;
import com.nickromero.seniorproject.views.adapters.PaperAdapter;

import java.util.ArrayList;

import data.models.Paper;
import data.enums.PaperRequestCodes;
import data.enums.PaperType;

/**
 * Created by nickromero on 12/23/16.
 */

/**
 * SavedPaperFragment holds all of the papers a user has marked saved.
 * These papers are not regularly cleaned or removed or updated without the user's intention
 */
public class SavedPaperFragment extends Fragment {

    /**
     * How many paper's should be displayed in the row of the grid
     */
    private final int SPAN_COUNT = 3;

    /**
     * Super class call
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Super class call to create a view for this paper
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.saved_fragment_view, container, false);

        RecyclerView mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerViewSavedFragment);

        GridLayoutManager mGridLayoutManager = new GridLayoutManager(getActivity(), SPAN_COUNT);

        mRecyclerView.setLayoutManager(mGridLayoutManager);

        mRecyclerView.setAdapter(new PaperController().getInstance(getContext()).mSavedAdapter);

        return rootView;

    }

    /**
     * Not used
     * @param savedInstanceState
     */
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {}

    /**
     * Handles a user selecting delete paper from the paper dialog
     * @param requestCode
     * @param resultCode what the user clicked in the dialog
     * @param data data containing information about the paper
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == PaperRequestCodes.DELETE_PAPER.getVal()) {
            new PaperController().getInstance(getContext()).removeFromAdapter(data.getIntExtra("position", -1),
                    PaperType.SAVED);
        }
    }
}

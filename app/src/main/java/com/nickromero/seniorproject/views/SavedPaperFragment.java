package com.nickromero.seniorproject.views;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nickromero.seniorproject.R;
import com.nickromero.seniorproject.views.adapters.RecyclerAdapter;

import java.util.ArrayList;

import data.Paper;
import utilities.ColumnCountingUtility;

/**
 * Created by nickromero on 12/23/16.
 */
public class SavedPaperFragment extends Fragment {

    private static final String MY_PAGE = "Saved";

    private static final String KEY_LAYOUT_MANAGER = "layoutManager";

    private RecyclerAdapter mAdapter;

    private final int SPAN_COUNT = 2;

    private ArrayList<Paper> mPapersList;

    private RecyclerView mRecyclerView;

    private GridLayoutManager mGridLayoutManager;

    public static SavedPaperFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(MY_PAGE, page);
        SavedPaperFragment fragment = new SavedPaperFragment();
        fragment.setArguments(args);

        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        initData();//Build the card views


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.saved_fragment_view, container, false);



        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerViewSavedFragment);

        int numberOfColumns = ColumnCountingUtility.calculateNoOfColumns(getContext());

        mGridLayoutManager = new GridLayoutManager(getActivity(), SPAN_COUNT);

        mRecyclerView.setLayoutManager(mGridLayoutManager);


        mAdapter = new RecyclerAdapter(mPapersList);




        mRecyclerView.setAdapter(mAdapter);

        return rootView;

    }



    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putSerializable(KEY_LAYOUT_MANAGER, "gridLayoutManager");

    }

    private void initData() {
        mPapersList = new ArrayList<>();
        ArrayList<String> authors = new ArrayList<>();
        authors.add("Nikola Tesla");
        authors.add("Denzel Washington");
        for (int i = 0; i < 6; i++)
            mPapersList.add(new Paper("Here is a reasonably" +
                    "long title for a paper, kinda longgggggggg.", authors, "url", "empty.pdf"));
    }

}

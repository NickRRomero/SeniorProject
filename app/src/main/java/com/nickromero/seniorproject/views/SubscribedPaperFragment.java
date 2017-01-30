package com.nickromero.seniorproject.views;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nickromero.seniorproject.R;
import com.nickromero.seniorproject.views.adapters.RecyclerAdapter;

import java.util.ArrayList;
import java.util.Arrays;

import data.Paper;
import utilities.ColumnCountingUtility;

/**
 * Created by nickromero on 12/23/16.
 */
public class SubscribedPaperFragment extends Fragment {

    private static final String MY_PAGE = "Saved";

    private RecyclerAdapter mAdapter;

    private final int SPAN_COUNT = 3;

    private ArrayList<Paper> mPapersList;

    private RecyclerView mRecyclerView;

    private GridLayoutManager mGridLayoutManager;

    public static SubscribedPaperFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(MY_PAGE, page);
        SubscribedPaperFragment fragment = new SubscribedPaperFragment();
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

        View rootView = inflater.inflate(R.layout.subscribed_fragment_view, container, false);



        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerViewSubscribedFragment);

       // int numberOfColumns = ColumnCountingUtility.calculateNoOfColumns(getContext());

        mGridLayoutManager = new GridLayoutManager(getActivity(), SPAN_COUNT);

        mRecyclerView.setLayoutManager(mGridLayoutManager);


        mAdapter = new RecyclerAdapter(mPapersList, 1, this);




        mRecyclerView.setAdapter(mAdapter);

        return rootView;
    }

    private void initData() {
        mPapersList = new ArrayList<>();



        mPapersList.add(new Paper("Survey of 5G Network: Architecture and Emerging Technologies",
                new ArrayList<String>(Arrays.asList("AKHIL GUPTA", "RAKESH KUMAR JHA")), "url", "5g.pdf"));
    }
}

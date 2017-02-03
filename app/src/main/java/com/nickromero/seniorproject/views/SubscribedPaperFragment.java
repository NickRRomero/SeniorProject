package com.nickromero.seniorproject.views;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nickromero.seniorproject.R;
import com.nickromero.seniorproject.views.adapters.PaperAdapter;

import java.util.ArrayList;
import java.util.Arrays;

import data.Paper;
import data.Subscription;

/**
 * Created by nickromero on 12/23/16.
 */
public class SubscribedPaperFragment extends Fragment {

    private static final String MY_PAGE = "Saved";

    private PaperAdapter mAdapter;

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


        mAdapter = new PaperAdapter(mPapersList, 1, this);




        mRecyclerView.setAdapter(mAdapter);

        return rootView;
    }

    private void initData() {
        mPapersList = new ArrayList<>();


        Paper spacePaper = new Paper("WHAT MIGHT WE LEARN FROM A FUTURE SUPERNOVA NEUTRINO SIGNAL?",
                new ArrayList<String>(Arrays.asList("PETR VOGEL")), "url", "space.pdf");
        mPapersList.add(spacePaper);

        spacePaper.addQualifier(new Subscription("Abstract", "Neutrinos", Color.BLUE));
        spacePaper.addQualifier(new Subscription("Title", "Supernova", Color.CYAN));
        spacePaper.addQualifier(new Subscription("Title", "Neutrinos", Color.LTGRAY));
        spacePaper.addQualifier(new Subscription("Year", "1999", Color.MAGENTA));
        spacePaper.addQualifier(new Subscription("Author", "Petr Vogel", Color.DKGRAY));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 6)
            System.out.println("HERe");
    }
}

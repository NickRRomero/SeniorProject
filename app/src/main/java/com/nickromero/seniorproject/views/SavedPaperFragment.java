package com.nickromero.seniorproject.views;

import android.graphics.Color;
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
import java.util.Arrays;

import data.Paper;
import data.Subscription;
import utilities.ColumnCountingUtility;

/**
 * Created by nickromero on 12/23/16.
 */
public class SavedPaperFragment extends Fragment {

    private static final String MY_PAGE = "Saved";

    private static final String KEY_LAYOUT_MANAGER = "layoutManager";

    private RecyclerAdapter mAdapter;

    private final int SPAN_COUNT = 3;

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


        mAdapter = new RecyclerAdapter(mPapersList, 0, this);




        mRecyclerView.setAdapter(mAdapter);

        return rootView;

    }



    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putSerializable(KEY_LAYOUT_MANAGER, "gridLayoutManager");

    }

    private void initData() {
        mPapersList = new ArrayList<>();





        Paper paper5 = new Paper("Survey of 5G Network: Architecture and Emerging Technologies",
                new ArrayList<String>(Arrays.asList("AKHIL GUPTA", "RAKESH KUMAR JHA")), "url", "5g.pdf");
        paper5.addQualifier(new Subscription("Title", "5G Network", Color.YELLOW));

        mPapersList.add(paper5);

        mPapersList.add(new Paper("Design of End-Effectors " +
                "for a Chemistry Automation Plant",
                new ArrayList<String>(Arrays.asList("Akshaya Kumar", "Kamila Pillearachichige", "Hamid Sharifi",
                        "Ben Shaw", "Frazer K. Noble")), "url", "chemistry.pdf"));

        mPapersList.add(new Paper("Does Gamification Work? — A Literature Review of Empirical Studies on Gamification",
                new ArrayList<String>(Arrays.asList("Juho Hamari", "Jonna Koivisto", "Harri Sarsa")), "url", "gamification.pdf"));

        mPapersList.add(new Paper("The Internet of Things for Health Care: A Comprehensive Survey",
                new ArrayList<String>(Arrays.asList("S. M. RIAZUL ISLAM", "DAEHAN KWAK", "MD. HUMAUN KABIR", "MAHMUD HOSSAIN"
                , "KYUNG-SUP KWAK")), "url", "healthcare.pdf"));


        Paper andPaper = new Paper("Internet of Things for Smart Cities",
                new ArrayList<String>(Arrays.asList("Andrea Zanella", "Nicola Bui", "Angelo Castellani"
                        , "Lorenzo Vangelista", "Michele Zorzi")), "url", "iot.pdf");

        andPaper.addQualifier(new Subscription("Author", "Andrea Zanella", Color.RED));

        mPapersList.add(andPaper);





        Paper securityPaper = new Paper("Information Security in Big Data: Privacy and Data Mining",
                new ArrayList<String>(Arrays.asList("LEI XU", "CHUNXIAO JIANG", "JIAN WANG",
                        "JIAN YUAN", "YONG REN")), "url", "issecurity.pdf");
        securityPaper.addQualifier(new Subscription("Abstract", "Security", Color.GREEN));

        mPapersList.add(securityPaper);



        mPapersList.add(new Paper("Predicting the Future With Social Media",
                new ArrayList<String>(Arrays.asList("Sitaram Asur", "Bernardo A. Huberman")), "url", "mediafuture.pdf"));

        mPapersList.add(new Paper("High-Performance Extreme Learning Machines: A Complete Toolbox for Big Data Applications",
                new ArrayList<String>(Arrays.asList("ANTON AKUSOK", "KAJ-MIKAEL BJÖRK", "YOAN MICHE", "AMAURY LENDASSE")), "url", "performance.pdf"));





    }

}

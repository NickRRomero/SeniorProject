package com.nickromero.seniorproject.views;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nickromero.seniorproject.R;
import com.nickromero.seniorproject.views.adapters.PaperAdapter;

import java.util.ArrayList;

import data.Paper;

import static android.view.View.GONE;

/**
 * Created by nickromero on 12/23/16.
 */
public class SuggestedPaperFragment extends Fragment {

    private static final String MY_PAGE = "Saved";

    private static final String KEY_LAYOUT_MANAGER = "layoutManager";

    private PaperAdapter mAdapter;




    private RecyclerView mRecyclerView;

    private LinearLayoutManager mLinearLayoutManager;

    private static final String MY_SUGGESTED_PAGE = "Suggested";

    public static SuggestedPaperFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(MY_SUGGESTED_PAGE, page);
        SuggestedPaperFragment fragment = new SuggestedPaperFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.suggested_fragment_view, container, false);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerViewSuggestedFragment);

        mLinearLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());

        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        mRecyclerView.setAdapter(PaperController.getInstance().mSuggestedAdapter);

        return rootView;

    }


}

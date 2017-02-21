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
public class SavedPaperFragment extends Fragment {

    private static final String MY_PAGE = "Saved";

    private static final String KEY_LAYOUT_MANAGER = "layoutManager";

    private PaperAdapter mAdapter;

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


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.saved_fragment_view, container, false);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerViewSavedFragment);

        mGridLayoutManager = new GridLayoutManager(getActivity(), SPAN_COUNT);

        mRecyclerView.setLayoutManager(mGridLayoutManager);

        mRecyclerView.setAdapter(PaperController.getInstance().mSavedAdapter);

        return rootView;

    }




    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putSerializable(KEY_LAYOUT_MANAGER, "gridLayoutManager");

    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == PaperRequestCodes.DELETE_PAPER.getVal()) {
            PaperController.getInstance().removeFromAdapter(data.getIntExtra("position", -1),
                    PaperType.SAVED);
        }
    }

    public void attachAdapter(PaperAdapter adapter) {

        mRecyclerView.setAdapter(adapter);

    }

}

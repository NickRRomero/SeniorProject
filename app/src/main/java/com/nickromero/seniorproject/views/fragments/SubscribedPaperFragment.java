package com.nickromero.seniorproject.views.fragments;

import android.content.Intent;
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
import java.util.List;

import data.models.Paper;
import data.enums.PaperRequestCodes;
import data.enums.PaperType;

/**
 * Created by nickromero on 12/23/16.
 */
public class SubscribedPaperFragment extends Fragment implements PaperContract.View {

    private static final String MY_PAGE = "Saved";

    private PaperAdapter mAdapter;

    private final int SPAN_COUNT = 3;

    private ArrayList<Paper> mPapersList;

    private RecyclerView mRecyclerView;

    private GridLayoutManager mGridLayoutManager;

    private PaperContract.Presenter mPresenter;


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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.subscribed_fragment_view, container, false);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerViewSubscribedFragment);


        mGridLayoutManager = new GridLayoutManager(getActivity(), SPAN_COUNT);

        mRecyclerView.setLayoutManager(mGridLayoutManager);

        mRecyclerView.setAdapter(PaperController.mSubscribedAdapter);

        mPresenter = new PaperPresenter(this);


        return rootView;
    }


    /**
     * This method is called when the view becomes visible to the user.
     * This will reload the list of games.
     */
    @Override
    public void onResume() {
        super.onResume();
        mPresenter.subscribe();
    }

    /**
     * This method is called when the view goes into the background.
     * This will clear any disposable network calls.
     */
    @Override
    public void onPause() {
        super.onPause();
        mPresenter.unsubscribe();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == PaperRequestCodes.MOVE_TO_SAVED.getVal()) {


            PaperController.getInstance(getContext()).addToSavedAdapter(
                    data.getIntExtra("position", -1),
                    PaperType.SUBSCRIBED);

        }
        else if (resultCode == PaperRequestCodes.DELETE_PAPER.getVal()) {
            PaperController.getInstance(getContext()).removeFromAdapter(data.getIntExtra("position", -1),
                    PaperType.SUBSCRIBED);
        }
    }

    public void attachAdapter(PaperAdapter adapter) {
        mRecyclerView.setAdapter(adapter);

    }

    @Override
    public void updatePapers(List<Paper> papers) {
        PaperController.getInstance(getContext()).addToSubscribedAdapter(papers);
    }

    @Override
    public void setPresenter(PaperContract.Presenter presenter) {
        mPresenter = presenter;
    }
}

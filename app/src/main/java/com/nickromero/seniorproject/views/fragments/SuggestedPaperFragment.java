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
public class SuggestedPaperFragment extends Fragment {

    private static final String MY_PAGE = "Saved";

    private static final String KEY_LAYOUT_MANAGER = "layoutManager";

    private PaperAdapter mAdapter;

    private int paperCount = 0;

    private EndlessRecyclerViewScrollListener scrollListener;

    private RecyclerView mRecyclerView;

    private LinearLayoutManager mLinearLayoutManager;

    private static final String MY_SUGGESTED_PAGE = "Suggested";

    private EditText searchBarText;

    private ImageView searchBarIcon;

    private static PaperController mPaperController;


    private String currentSearchTerm;

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

        mPaperController = PaperController.getInstance(getContext());

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerViewSuggestedFragment);

        mLinearLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());

        searchBarText = (EditText) rootView.findViewById(R.id.searchEditText);

        searchBarIcon = (ImageView) rootView.findViewById(R.id.searchBarIcon);
        searchBarIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentSearchTerm = searchBarText.getText().toString();
                if (!currentSearchTerm.isEmpty()) {
                    scrollListener.onLoadMore(0, 15, mRecyclerView);
                }

            }
        });

        mRecyclerView.setLayoutManager(mLinearLayoutManager);

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

package com.nickromero.seniorproject.views;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nickromero.seniorproject.R;

/**
 * Created by nickromero on 12/23/16.
 */
public class SuggestedPaperFragment extends Fragment {


    private static final String MY_PAGE = "Saved";

    public static SuggestedPaperFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(MY_PAGE, page);
        SuggestedPaperFragment fragment = new SuggestedPaperFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.suggested_fragment_view, container, false);

    }


}

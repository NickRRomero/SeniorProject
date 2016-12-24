package com.nickromero.seniorproject.views;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nickromero.seniorproject.R;

/**
 * Created by nickromero on 12/23/16.
 */
public class SavedPaperFragment extends Fragment {

    private static final String MY_PAGE = "Saved";

    public static SavedPaperFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(MY_PAGE, page);
        SavedPaperFragment fragment = new SavedPaperFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.saved_fragment_view, container, false);

    }

}

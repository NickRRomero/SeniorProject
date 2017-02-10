package com.nickromero.seniorproject.views;

import android.app.AlertDialog;
import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nickromero.seniorproject.R;

import data.Paper;
import data.enums.PaperRequestCodes;

/**
 * Created by nickromero on 1/29/17.
 */

public class PaperDialogFragment extends DialogFragment {

    private AlertDialog.Builder mDialogBuilder;

    private int mWhichDialogToShow;

    private String sDialogTitle;

    private static Fragment mFragmentActivity;

    private static SubscribedPaperFragment mSubscribedPaperFragment;

    private Paper mPaper;

    //Position of a paper associated with this view
    private int mPaperPosition;

    private final String CLOSE = "Close";
    private final String DELETE_PAPER = "Delete this Paper";
    private final String SAVE = "Save this Paper";

    /**
     * Member variables used to create the two buttons at the bottom of a dialog
     */
    private String sNegativeButtonText;
    private String sPositiveButtonText;


    public PaperDialogFragment() {}

    public PaperDialogFragment newInstance(int whichDialogToShow, Fragment parentActivity, Paper paper, int paperPosition) {
        PaperDialogFragment newFragment = new PaperDialogFragment();

        Bundle args = new Bundle();
        args.putSerializable("whichDialog", whichDialogToShow);
        args.putSerializable("paper", paper);
        args.putSerializable("position", paperPosition);
        System.out.println("Paper : " + paperPosition);
        newFragment.setArguments(args);

        mFragmentActivity = parentActivity;

        return newFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mWhichDialogToShow =  getArguments().getInt("whichDialog");
        mPaper = (Paper) getArguments().getSerializable("paper");
        mPaperPosition = getArguments().getInt("position");

        switch (mWhichDialogToShow) {
            //Dialog on a saved paper fragment
            case 0:
                sPositiveButtonText = DELETE_PAPER;
                sNegativeButtonText = CLOSE;
                break;
            case 1:
                sPositiveButtonText = SAVE;
                sNegativeButtonText = DELETE_PAPER;
                break;

        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mDialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        //Set title of dialog box to be a papers title
        mDialogBuilder.setTitle(mPaper.getTitle());

        View paperDialogLayout = (LinearLayout) inflater.inflate(R.layout.custom_paper_dialog, null);
        LinearLayout authors = (LinearLayout) paperDialogLayout.findViewById(R.id.authors_dialog);
        TextView paper_abstract = (TextView) paperDialogLayout.findViewById(R.id.paper_abstract);

        for (String author : mPaper.getAuthors()) {
            TextView nextAuthor = (TextView) inflater.inflate(R.layout.text_view, null);

            nextAuthor.setText(author);
            authors.addView(nextAuthor);
        }

       //Change with real data
       // paper_abstract.setText(mPaper.getAbstract());
        paper_abstract.setText("\"Lorem ipsum dolor sit amet, consectetur adipiscing elit, " +
                "sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. " +
                "Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea " +
                "commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse" +
                " cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident," +
                " sunt in culpa qui officia deserunt mollit anim id est laborum.\"");


        mDialogBuilder.setView(paperDialogLayout);

        mDialogBuilder.setPositiveButton(sPositiveButtonText, new DialogInterface.OnClickListener() {

            @Override

            public void onClick(DialogInterface dialogInterface, int i) {
                System.out.println(mWhichDialogToShow);
                if (mWhichDialogToShow == 0)
                    deletePaperDialogClick();
                if (mWhichDialogToShow == 1)
                    addPaperToSavedSectionDialogClick();
            }
        });

        mDialogBuilder.setNegativeButton(sNegativeButtonText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if (mWhichDialogToShow == 1)
                    deletePaperDialogClick();
            }
        });

        return mDialogBuilder.create();
    }

    private void addPaperToSavedSectionDialogClick() {
        Intent returnIntent = new Intent().putExtra("position", mPaperPosition);
        getTargetFragment().onActivityResult(getTargetRequestCode(), 6, returnIntent);
        dismiss();
    }

    private void deletePaperDialogClick() {

        switch (mWhichDialogToShow) {

            //Deleting a paper from the SAVED paper fragment
            case 0:
            case 1:
                Intent returnIntent = new Intent().putExtra("position", mPaperPosition);
                getTargetFragment().onActivityResult(getTargetRequestCode(),
                        PaperRequestCodes.DELETE_PAPER.getVal(),
                        returnIntent);
                dismiss();
                break;


        }
    }
}

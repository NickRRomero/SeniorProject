package com.nickromero.seniorproject.views.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nickromero.seniorproject.R;

import java.util.ArrayList;

import data.models.Paper;

/**
 * Created by nickromero on 2/5/17.
 */

public class SuggestedPaperAdapter extends RecyclerView.Adapter<SuggestedPaperAdapter.PaperHolder> {

    private ArrayList<Paper> mPapers;



    private Fragment mParentFragment;

    /**
     * @param papers
     */
    public SuggestedPaperAdapter(ArrayList<Paper> papers, Fragment parentFragment) {

        mPapers = papers;
        mParentFragment = parentFragment;
    }

    @Override
    public PaperHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.suggested_paper_layout, parent, false);

        return new PaperHolder(parent.getContext(), inflatedView);
    }


    @Override
    public void onBindViewHolder(PaperHolder holder, int position) {
        Paper paper = mPapers.get(position);
        holder.bindPaper(paper, position);
    }

    @Override
    public int getItemCount() {
        return mPapers.size();
    }

    public class PaperHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        private final TextView mPapersAuthors;
        private final TextView mPaperTitle;
        private final TextView mSimilarityScore;
        private final Context mContext;
        private Paper mPaper;
        private int mPapersPosition;

        public PaperHolder(Context context, View itemView) {
            super(itemView);

            mPapersAuthors = (TextView) itemView.findViewById(R.id.suggested_paper_authors);
            mPaperTitle = (TextView) itemView.findViewById(R.id.suggested_paper_title);
            mSimilarityScore = (TextView) itemView.findViewById(R.id.suggested_paper_similarity_score);
            mContext = context;

        }

        public void bindPaper(Paper paper, int position) {
            mPaper = paper;
            mPapersPosition = position;
            mPapersAuthors.setText("");

            /*Dynamically add authors
            for (int i = 0; i < mPaper.getAuthors().size() && i < 3; i++) {
                String oldText = mPapersAuthors.getText().toString();

                mPapersAuthors.setText(oldText + ", ");
            }*/
            //mPapersAuthors.setText(mPaper.getAuthors().get(0).toString());
            mSimilarityScore.setText("Score:\n" + (Math.abs(position - 20)));
            mPaperTitle.setText(mPaper.getTitle());

        }

        @Override
        public void onClick(View v) {

        }
    }
}

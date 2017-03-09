package com.nickromero.seniorproject.views.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.nickromero.seniorproject.R;

import java.util.ArrayList;
import java.util.List;

import data.models.Paper;

/**
 * Created by nickromero on 2/5/17.
 */

/**
 * The SuggestedPaperAdapter holds the papers shown on the Suggested Fragment. These papers have different
 * views and formatting associated with them.
 */
public class SuggestedPaperAdapter extends RecyclerView.Adapter<SuggestedPaperAdapter.PaperHolder> {

    /**
     * Collection of paper adapters
     */
    private List<Paper> mPapers;

    /**
     * Int used to allow papers to know if they should expand when clicked
     */
    private int mExpandedPosition = -1;

    /**
     * Fragment using this adapter
     */
    private Fragment mParentFragment;

    /**
     * Constructor for SuggestedPaperAdapter
     * @param papers List collection of papers
     * @param parentFragment containing fragment
     */
    public SuggestedPaperAdapter(List<Paper> papers, Fragment parentFragment) {
        mPapers = papers;
        mParentFragment = parentFragment;
    }

    /**
     * Extended method to create a new holder
     * @param parent
     * @param viewType
     * @return PaperHolder
     */
    @Override
    public PaperHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.suggested_paper_layout, parent, false);


        return new PaperHolder(parent.getContext(), inflatedView);
    }


    /**
     * Extended method to bind paper to holder
     * @param holder PaperHolder to bind to
     * @param position of the paper in the adapter
     */
    @Override
    public void onBindViewHolder(PaperHolder holder, int position) {
        Paper paper = mPapers.get(position);

        final boolean isExpanded = position == mExpandedPosition;

        //Following block handles views expending and collapsing when clicked
        holder.expandArea.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
        holder.itemView.setActivated(isExpanded);
        holder.itemView.setOnClickListener(v -> {
            mExpandedPosition = isExpanded ? -1 : position;
            notifyDataSetChanged();
        });

        //This is where a user needs to be redirected to downloading a paper from
        //the link associated with this paper. Currently it doesn't work
        //as there are issues with the webview/IEEE access on the device
        holder.itemView.setOnLongClickListener(v -> {
            new MaterialDialog.Builder(mParentFragment.getContext()).
                    title("Navigate to Site\n\n" + paper.getURL())
                    .content("Here you can save this paper to your device" +
                            "for off-line viewing.")
                    .positiveText("Go to URL")
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                           /* WebView mWebView = (WebView) LayoutInflater.from(mParentFragment.getContext())
                                    .inflate(R.layout.paper_webview, null);
                            mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);

                            mWebView.loadUrl(paper.getURL());*/
                        }
                    })
                    .negativeText("Close")
                    .onNegative(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            dialog.dismiss();
                        }
                    })
                    .show();
            return true;
        });
        holder.bindPaper(paper, position);
    }


    /**
     * Get the number of papers in the adapter
     * @return int number of papers
     */
    @Override
    public int getItemCount() {
        return mPapers.size();
    }

    /**
     * Add a paper to the adapter
     * @param paper to add to the adapter
     */
    public void addItem(Paper paper) {
        mPapers.add(paper);
        notifyDataSetChanged();
    }
    /**
     * Inner class used to represent a Paper Holder object. Paper Holder handles the logic
     * of creating and initializing a paper.
     */
    class PaperHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        /**
         * View for a paper's authors
         */
        private final TextView mPapersAuthors;

        /**
         * View for a paper's title
         */
        private final TextView mPaperTitle;

        /**
         * View for a paper's abastract
         */
        private final TextView mPaperAbstract;

        /**
         * View for the year a Paper was written in
         */
        private final TextView mPaperYear;

        /**
         * View for the publisher of a paper
         */
        private final TextView mPaperPublisher;

        /**
         * View for the ISSN of a paper
         */
        private final TextView mPaperISSN;

        /**
         * Paper bound to this holder
         */
        private Paper mPaper;

        /**
         * Viewgroup used to handle a paper expanding
         */
        private RelativeLayout expandArea;

        /**
         * Constructor for a paperHolder
         * @param context calling context
         * @param itemView
         */
        PaperHolder(Context context, View itemView) {
            super(itemView);

            mPapersAuthors = (TextView) itemView.findViewById(R.id.suggested_paper_authors);
            mPaperTitle = (TextView) itemView.findViewById(R.id.suggested_paper_title);
            mPaperAbstract = (TextView) itemView.findViewById(R.id.suggested_paper_abstract);
            expandArea = (RelativeLayout) itemView.findViewById(R.id.suggested_paper_info);
            mPaperYear = (TextView) itemView.findViewById(R.id.year);
            mPaperPublisher = (TextView) itemView.findViewById(R.id.publisher);
            mPaperISSN = (TextView) itemView.findViewById(R.id.issn);
        }

        /**
         * Bind the paper object to this view holder
         * @param paper to bind
         * @param position of the paper
         */
        void bindPaper(Paper paper, int position) {
            mPaper = paper;
            mPapersAuthors.setText("");
            mPaperAbstract.setText(paper.getAbstract());
            mPaperYear.setText(paper.getPubYear());
            mPaperPublisher.setText(paper.getPublisher());
            mPaperISSN.setText(paper.getISSN());

            //mPapersAuthors.setText(mPaper.getAuthors());
            mPaperTitle.setText(mPaper.getTitle());
        }

        @Override
        public void onClick(View v) {}
    }
}
